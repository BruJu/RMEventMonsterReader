package fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.inliner;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

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

	private Map<Integer, VivaciteVariable> vivacites = new HashMap<>();
/*
	private Set<Integer> variablesMortes = new HashSet<>();
	private Map<Integer, InstructionGenerale> variablesVivantes = new HashMap<>();
*/
	final Set<InstructionAffectation> instructionsAIgnorer = new HashSet<>(); // Mortes + inlinées
	final Map<InstructionGenerale, List<InstructionAffectation>> affectationsInlinables = new HashMap<>();
	
	public void noterExpression(InstructionGenerale instruction, Expression expression) {
		ListeurDePresence listeur = new ListeurDePresence();
		listeur.visit(expression);
		Set<ExprVariable> variablesPresentes = listeur.variablesPresentes;
		
		for (ExprVariable variable : variablesPresentes) {
			int numeroDeCase = variable.idVariable;

			Utilitaire.Maps.getX(vivacites, numeroDeCase, VivaciteVariable::new).faireVivre(instruction);

			/*
			boolean etaitMort = variablesMortes.remove(numeroDeCase);
			
			modifierVariablesVivantes(etaitMort, numeroDeCase, instruction);*/
		}
	}
/*
	private void modifierVariablesVivantes(boolean etaitMort, int numeroDeCase, InstructionGenerale instruction) {
		if (variablesVivantes.containsKey(numeroDeCase)) {
			variablesVivantes.put(numeroDeCase, null);
		} else {
			if (etaitMort) {
				variablesVivantes.put(numeroDeCase, instruction);
			}
		}
	}
*/
	@Override
	public void visit(InstructionAffectation instructionAffectation) {
		int numeroDeCase = instructionAffectation.variableAssignee.idVariable;
		Utilitaire.Maps.getX(vivacites, numeroDeCase, VivaciteVariable::new).tuer(instructionAffectation, this);

		/*
		if (!tuer(instructionAffectation.variableAssignee.idVariable, instructionAffectation)) {
			noterExpression(instructionAffectation, instructionAffectation.expression);
		}*/
	}

	private boolean tuer(int numeroVariableAffectee, InstructionAffectation instructionActuelle) {
		return true;
		/*
		if (variablesMortes.contains(numeroVariableAffectee)) {
			instructionsAIgnorer.add(instructionActuelle);
			return true;
		} else {
			variablesMortes.add(numeroVariableAffectee);

			InstructionGenerale utilisatrice = variablesVivantes.remove(numeroVariableAffectee);
			if (utilisatrice != null) {
				instructionsAIgnorer.add(instructionActuelle);
				Utilitaire.Maps.ajouterElementDansListe(affectationsInlinables, utilisatrice, instructionActuelle);
			}

			return false;
		}
		*/
	}

	@Override
	public void visit(BlocConditionnel blocConditionnel) {
		noterCondition(blocConditionnel, blocConditionnel.condition);

		Deviation deviation = new Deviation(vivacites);

		vivacites = deviation.filsVrai;
		blocConditionnel.siVrai.acceptInverse(this);

		vivacites = deviation.filsFaux;
		blocConditionnel.siFaux.acceptInverse(this);

		vivacites = deviation.consolider();




		/*
		variablesVivantes.clear();
		Set<Integer> variablesMortesSi = new HashSet<>(variablesMortes);
		Set<Integer> variablesMortesSinon = new HashSet<>(variablesMortes);
		Set<Integer> variablesMortesApres = variablesMortes;


		variablesMortes = variablesMortesSi;
		blocConditionnel.siVrai.acceptInverse(this);
		variablesVivantes.clear();
		variablesMortes = variablesMortesSinon;
		blocConditionnel.siFaux.acceptInverse(this);
		variablesVivantes.clear();

		variablesMortes = variablesMortesApres;
		variablesMortes.clear();
		
		variablesMortesSi.forEach(caseMemoire -> {
			if (variablesMortesSinon.contains(caseMemoire)) {
				variablesMortes.add(caseMemoire);
			}
		});
		*/
	}

	private void noterCondition(BlocConditionnel blocConditionnel, Condition condition) {
		if (condition instanceof ConditionVariable) { // La liste des conditions n'est pas amenée à évoluer
			ConditionVariable cVariable = (ConditionVariable) condition;
			
			noterExpression(blocConditionnel, cVariable.gauche);
			noterExpression(blocConditionnel, cVariable.droite);
		}
	}

	@Override
	public void visit(InstructionAffichage instructionAffichage) {
		// Ignoré
	}

}
