package fr.bruju.rmeventreader.implementation.detectiondeformules.transformations.inliner;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import fr.bruju.rmeventreader.implementation.detectiondeformules.modele.algorithme.BlocConditionnel;
import fr.bruju.rmeventreader.implementation.detectiondeformules.modele.algorithme.InstructionAffectation;
import fr.bruju.rmeventreader.implementation.detectiondeformules.modele.algorithme.InstructionAffichage;
import fr.bruju.rmeventreader.implementation.detectiondeformules.modele.algorithme.InstructionGenerale;
import fr.bruju.rmeventreader.implementation.detectiondeformules.modele.algorithme.VisiteurDAlgorithme;
import fr.bruju.rmeventreader.implementation.detectiondeformules.modele.condition.Condition;
import fr.bruju.rmeventreader.implementation.detectiondeformules.modele.condition.ConditionVariable;
import fr.bruju.rmeventreader.implementation.detectiondeformules.modele.expression.Expression;
import fr.bruju.rmeventreader.implementation.detectiondeformules.modele.expression.ExprVariable;
import fr.bruju.util.Lambda;
import fr.bruju.util.MapsUtils;

/**
 * Cette classe a pour but d'analyser un algorithme et comment sont utilisées les instructions.
 * <br>Le résultat de l'analyse fait apparaître les instructions inutiles (celles dont le résultat n'est soit jamais
 * utilisé, soit n'est utilisé qu'une fois) et les instructions dont la valeur des variables utilisées pourrait être
 * utilisé directement (variables utilisées qu'une fois).
 * <br><br>Exemple 1 : a = 3; a = 4; -> La ligne a = 3 est inutile
 * <br>Exemple 2 : a = 3; b = a; a = 4; -> a = 3 pourrait être supprimé si on réecrit la ligne b = a en b = 3.
 * <br>Exemple 3 : a = 3; b = a; c = a; a = 4; -> On garde a = 3 car a est utilisé plusieurs fois.
 */
public class AnalyseurDUtilisationsDesInstructions implements VisiteurDAlgorithme {
	// == RESULTAT ==

	/** Liste des affectations mortes + inlinables (ie à supprimer lors de la réecriture) */
	final Set<InstructionAffectation> instructionsAIgnorer = new HashSet<>();
	/** Association Instruction -> liste des instructions qu'elle peut intégrer */
	final Map<InstructionGenerale, List<InstructionAffectation>> affectationsInlinables = new HashMap<>();


	// == ANALYSE ==

	/** Liste des variables vivantes et l'instruction qui l'utilise si il n'y en a qu'une */
	private Map<Integer, InstructionGenerale> variablesVivantes = new HashMap<>();

	// Permet de détecter les conditions dont toutes les branches sont mortes (ie l'évaluation de la condition n'a
	// aucun impact sur le déroulement et les variables utilisées peuvent ne pas être déclarées vivantes)
	private int nombreDiInstructionsVisitees = 0;
	private int nombreDiInstructionsIgnorees = 0;

	/**
	 * Crée un analyseur d'utilisations des instructions en vue de détecter les instructions mortes et les instructions
	 * dont le résultat n'est utilisé qu'une fois
	 * @param variablesVivantes La liste des variables vivantes à la sortie
	 */
	public AnalyseurDUtilisationsDesInstructions(List<ExprVariable> variablesVivantes) {
		for (ExprVariable variablesVivante : variablesVivantes) {
			this.variablesVivantes.put(variablesVivante.idVariable, null);
		}
	}

	/**
	 * Note que les variables utilisées dans l'expression sont utilisées par l'instruction donnée
	 * @param instruction L'instruction qui utilise l'expression
	 * @param expression L'expression
	 */
	public void noterExpression(InstructionGenerale instruction, Expression expression) {
		Set<ExprVariable> variablesPresentes = ListeurDeVariablesPresentesDansUneExpression.lister(expression);
		
		for (ExprVariable variable : variablesPresentes) {
			int numeroDeCase = variable.idVariable;

			if (variablesVivantes.containsKey(numeroDeCase)) {
				variablesVivantes.put(numeroDeCase, null);
			} else {
				variablesVivantes.put(numeroDeCase, instruction);
			}
		}
	}


	@Override
	public void visit(InstructionAffectation instructionAffectation) {
		// Si la variable affectée est morte : supprimable
		// Si la variable affectée n'est utilisée qu'une fois : inlinable
		// Si non morte : les variables utilisées par cette affectation deviennent vivantes

		nombreDiInstructionsVisitees++;
		int numeroDeCase = instructionAffectation.variableAssignee.idVariable;

		if (!variablesVivantes.containsKey(numeroDeCase)) {
			// Variable morte
			instructionsAIgnorer.add(instructionAffectation);
			nombreDiInstructionsIgnorees++;
		} else {
			InstructionGenerale utilisatrice = variablesVivantes.remove(numeroDeCase);

			if (utilisatrice != null) {
				// Inlinable
				instructionsAIgnorer.add(instructionAffectation);
				nombreDiInstructionsIgnorees++;
				MapsUtils.ajouterElementDansListe(affectationsInlinables, utilisatrice, instructionAffectation);
			}

			noterExpression(instructionAffectation, instructionAffectation.expression);
		}
	}

	@Override
	public void visit(BlocConditionnel blocConditionnel) {
		// Visite des branches vrai et faux en considérant enlevant la possibilité d'inliner
		int differenceIgnoreesAvant = nombreDiInstructionsVisitees - nombreDiInstructionsIgnorees;

		// Initialisation de la liste des variables vivantes dans les différentes branches
		Map<Integer, InstructionGenerale> vrai = new HashMap<>();
		Map<Integer, InstructionGenerale> faux = new HashMap<>();

		for (Integer idVariable : variablesVivantes.keySet()) {
			vrai.put(idVariable, null);
			faux.put(idVariable, null);
		}

		// Exploration si
		variablesVivantes = vrai;
		blocConditionnel.siVrai.acceptInverse(this);
		vrai = variablesVivantes; // La réaffectation est utile car la table de hashage peut avoir changé (cf [1])

		// Exploration sinon
		variablesVivantes = faux;
		blocConditionnel.siFaux.acceptInverse(this);
		faux = variablesVivantes;

		// Fin d'exploration
		variablesVivantes = new HashMap<>(); // [1] Change la table de hashage utilisée pour le contexte de base
		MapsUtils.combinerNonNull(variablesVivantes, vrai, faux, Lambda::referenceEgaleOuNull);

		// Si les branches ne sont pas vides, on rend vivantes les variables utilisées dans la condition
		int differenceIgnoreesApres = nombreDiInstructionsVisitees - nombreDiInstructionsIgnorees;

		if (differenceIgnoreesAvant != differenceIgnoreesApres) {
			noterCondition(blocConditionnel, blocConditionnel.condition);
		}
	}


	/**
	 * Si la condition est une condition portant sur des variables, rend vivantes les variables utilisées lors du
	 * test.
	 * @param blocConditionnel L'instruction de bloc conditionnel
	 * @param condition La condition
	 */
	private void noterCondition(BlocConditionnel blocConditionnel, Condition condition) {
		if (condition instanceof ConditionVariable) { // La liste des conditions n'est pas amenée à évoluer
			ConditionVariable cVariable = (ConditionVariable) condition;
			
			noterExpression(blocConditionnel, cVariable.gauche);
			noterExpression(blocConditionnel, cVariable.droite);
		}
	}

	@Override
	public void visit(InstructionAffichage instructionAffichage) {
		nombreDiInstructionsVisitees++;
	}
}
