package fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.inliner;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.sun.org.apache.bcel.internal.generic.Instruction;
import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.algorithme.BlocConditionnel;
import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.algorithme.InstructionAffectation;
import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.algorithme.InstructionAffichage;
import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.algorithme.InstructionGenerale;
import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.algorithme.VisiteurDAlgorithme;
import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.condition.Condition;
import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.condition.ConditionVariable;
import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.expression.Expression;
import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.expression.ExprVariable;
import fr.bruju.rmeventreader.utilitaire.Utilitaire;

public class DetecteurDeSimplifications implements VisiteurDAlgorithme {

	//private Map<Integer, VivaciteVariable> vivacites = new HashMap<>();

	private Map<Integer, InstructionGenerale> variablesVivantes = new HashMap<>();

	private int nombreDiInstructionsVisitees = 0;
	private int nombreDiInstructionsIgnorees = 0;

	final Set<InstructionAffectation> instructionsAIgnorer = new HashSet<>(); // Mortes + inlinées
	final Map<InstructionGenerale, List<InstructionAffectation>> affectationsInlinables = new HashMap<>();
	
	public void noterExpression(InstructionGenerale instruction, Expression expression) {
		ListeurDePresence listeur = new ListeurDePresence();
		listeur.visit(expression);
		Set<ExprVariable> variablesPresentes = listeur.variablesPresentes;
		
		for (ExprVariable variable : variablesPresentes) {
			int numeroDeCase = variable.idVariable;

			if (variablesVivantes.containsKey(numeroDeCase)) {
				variablesVivantes.put(numeroDeCase, null);
			} else {
				variablesVivantes.put(numeroDeCase, instruction);
			}

			//Utilitaire.Maps.getX(vivacites, numeroDeCase, VivaciteVariable::new).faireVivre(instruction);
		}
	}


	@Override
	public void visit(InstructionAffectation instructionAffectation) {
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
				Utilitaire.Maps.ajouterElementDansListe(affectationsInlinables, utilisatrice, instructionAffectation);
			}

			noterExpression(instructionAffectation, instructionAffectation.expression);
		}



		//Utilitaire.Maps.getX(vivacites, numeroDeCase, VivaciteVariable::new).tuer(instructionAffectation, this);
	}

	@Override
	public void visit(BlocConditionnel blocConditionnel) {

		int differenceIgnoreesAvant = nombreDiInstructionsVisitees - nombreDiInstructionsIgnorees;

		Map<Integer, InstructionGenerale> origine = this.variablesVivantes;
		Map<Integer, InstructionGenerale> vrai = new HashMap<>(origine);
		Map<Integer, InstructionGenerale> faux = new HashMap<>(origine);

		variablesVivantes = vrai;
		blocConditionnel.siVrai.acceptInverse(this);

		variablesVivantes = faux;
		blocConditionnel.siFaux.acceptInverse(this);


		variablesVivantes = origine;

		vrai.keySet().forEach(id -> variablesVivantes.put(id, null));
		faux.keySet().forEach(id -> variablesVivantes.put(id, null));

		int differenceIgnoreesApres = nombreDiInstructionsVisitees - nombreDiInstructionsIgnorees;

		if (differenceIgnoreesAvant != differenceIgnoreesApres) {
			noterCondition(blocConditionnel, blocConditionnel.condition);
		}




		/*
		Deviation deviation = new Deviation(vivacites);

		vivacites = deviation.filsVrai;
		blocConditionnel.siVrai.acceptInverse(this);

		vivacites = deviation.filsFaux;
		blocConditionnel.siFaux.acceptInverse(this);

		vivacites = deviation.consolider();

		*/
	}

	private void noterCondition(BlocConditionnel blocConditionnel, Condition condition) {
		// TODO : la condition est vide, ne pas faire vivre les variables qu'elle utilise
		if (condition instanceof ConditionVariable) { // La liste des conditions n'est pas amenée à évoluer
			ConditionVariable cVariable = (ConditionVariable) condition;
			
			noterExpression(blocConditionnel, cVariable.gauche);
			noterExpression(blocConditionnel, cVariable.droite);
		}
	}

	@Override
	public void visit(InstructionAffichage instructionAffichage) {
		nombreDiInstructionsVisitees++;
		// Ignoré
	}

}
