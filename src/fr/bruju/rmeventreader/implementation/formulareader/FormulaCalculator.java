package fr.bruju.rmeventreader.implementation.formulareader;

import java.util.HashMap;
import java.util.Map;

import fr.bruju.rmeventreader.actionmakers.actionner.ActionMakerWithConditionalInterest;
import fr.bruju.rmeventreader.actionmakers.actionner.Operator;
import fr.bruju.rmeventreader.actionmakers.donnees.ValeurAleatoire;
import fr.bruju.rmeventreader.actionmakers.donnees.ValeurFixe;
import fr.bruju.rmeventreader.actionmakers.donnees.Variable;
import fr.bruju.rmeventreader.formule.Valeur;
import fr.bruju.rmeventreader.formule.base.ValeurNumerique;
import fr.bruju.rmeventreader.formule.base.ValeurStatistique;
import fr.bruju.rmeventreader.formule.composant.Personnage;
import fr.bruju.rmeventreader.formule.composant.Statistique;
import fr.bruju.rmeventreader.formule.operations.Calcul;
import fr.bruju.rmeventreader.implementation.monsterlist.Pair;
import fr.bruju.rmeventreader.utilitaire.Ensemble;
import fr.bruju.rmeventreadercomplement.AeStatsPerso;

public class FormulaCalculator implements ActionMakerWithConditionalInterest {
	private static final int TERMINATOR_EVENT_MAP_NUMB = 77;
	private static final int TERMINATOR_EVENT_MAP_PAGE = 1;
	
	private static final int[] ENTER_IN_SWITCHES = {182};
	private static final int VARIABLE_CIBLE = 42;
	private static final int VALEUR_CIBLE = 70;
	
	private static final int VARIABLE_DEGATS_INFLIGES = 1181;
	
	private Map<Integer, Valeur> variableUtilisee = new HashMap<>();
	
	
	private Valeur sortie;

	private void fixerLaSortie() {
		sortie = variableUtilisee.get(VARIABLE_DEGATS_INFLIGES);
	}
	
	public Valeur getSortie() {
		return sortie;
	}
	
	private Valeur getValue(ValeurFixe value) {
		return new ValeurNumerique(value.get(), value.get());
	}

	private Valeur getValue(ValeurAleatoire value) {
		return new ValeurNumerique(value.getMin(), value.getMax());
	}

	private Valeur getValue(Variable value) {
		Pair<Personnage, Statistique> variableDepart = getStatName(value.get());
		
		if (variableDepart != null) {
			return new ValeurStatistique(variableDepart.getLeft(), variableDepart.getRight());
		} else {
			return variableUtilisee.getOrDefault(value.get(), new ValeurNumerique(0, 0));
		}
	}

	private Pair<Personnage, Statistique> getStatName(int value) {
		Map<Integer, Pair<Personnage, Statistique>> mapStats = AeStatsPerso.get(); 
		
		return mapStats.get(value);
	}

	private String getSymbole(Operator operator) {
		switch (operator) {
		case DIVIDE:
			return "/";
		case MINUS:
			return "-";
		case MODULO:
			return "%";
		case PLUS:
			return "+";
		case TIMES:
			return "*";
		default:
			break;
		}
		
		return "?";
	}

	
	/* ==========
	 * Conditions
	 * ========== */

	// Switch
	
	@Override
	public boolean caresAboutCondOnSwitch(int number, boolean value) {
		return Ensemble.appartient(number, ENTER_IN_SWITCHES);
	}

	@Override
	public void condOnSwitch(int number, boolean value) { }
	
	
	// Variable
	
	@Override
	public boolean caresAboutCondOnVariable(int leftOperandValue, Operator operatorValue, ValeurFixe returnValue) {
		// TODO
		return leftOperandValue == VARIABLE_CIBLE && returnValue.get() == VALEUR_CIBLE;
	}

	@Override
	public void condOnVariable(int leftOperandValue, Operator operatorValue, ValeurFixe returnValue) {
		if (leftOperandValue == VARIABLE_CIBLE && returnValue.get() == VALEUR_CIBLE) {
			return;
		}
		
		// TODO
	}


	// Else End
	
	@Override
	public void condElse() {
		throw new RuntimeException("condElse");
	}

	@Override
	public void condEnd() {
		
	}

	/* ==========
	 * Actions
	 * ========== */

	@Override
	public void changeVariable(Variable variable, Operator operator, ValeurFixe returnValue) {
		chgVar(variable.get(), operator, getValue(returnValue));
	}

	@Override
	public void changeVariable(Variable variable, Operator operator, ValeurAleatoire returnValue) {
		chgVar(variable.get(), operator, getValue(returnValue));
	}
	
	@Override
	public void changeVariable(Variable variable, Operator operator, Variable returnValue) {
		chgVar(variable.get(), operator, getValue(returnValue));
	}

	private void chgVar(int idVariableAModifier, Operator operator, Valeur rightValue) {
		if (operator == Operator.AFFECTATION) {
			variableUtilisee.put(idVariableAModifier, rightValue);
		} else {
			String symbole = getSymbole(operator);
			
			variableUtilisee.put(idVariableAModifier, new Calcul(variableUtilisee.get(idVariableAModifier), symbole, rightValue));
		}
	}

	@Override
	public void callMapEvent(int eventNumber, int eventPage) {
		if (eventNumber == TERMINATOR_EVENT_MAP_NUMB && eventPage == TERMINATOR_EVENT_MAP_PAGE) {
			fixerLaSortie();
		}
	}


}
