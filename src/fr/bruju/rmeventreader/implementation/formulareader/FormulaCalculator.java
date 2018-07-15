package fr.bruju.rmeventreader.implementation.formulareader;

import java.util.HashMap;
import java.util.Map;

import fr.bruju.rmeventreader.actionmakers.actionner.ActionMakerWithConditionalInterest;
import fr.bruju.rmeventreader.actionmakers.actionner.Operator;
import fr.bruju.rmeventreader.actionmakers.actionner.ReturnValue;
import fr.bruju.rmeventreader.actionmakers.actionner.SwitchNumber;
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
	
	private Valeur getValue(ReturnValue returnValue) {
		switch (returnValue.type) {
		case VALUE:
			return new ValeurNumerique(returnValue.value, returnValue.borneMax);
		case VARIABLE:
			Pair<Personnage, Statistique> variableDepart = getStatName(returnValue.value);
			
			if (variableDepart != null) {
				return new ValeurStatistique(variableDepart.getLeft(), variableDepart.getRight());
			} else {
				return variableUtilisee.getOrDefault(returnValue.value, new ValeurNumerique(0, 0));
			}
		case POINTER:
			throw new RuntimeException("Ne gere pas les pointeurs");
		}

		throw new RuntimeException("Magic");
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
	public boolean caresAboutCondOnVariable(int leftOperandValue, Operator operatorValue, ReturnValue returnValue) {
		// TODO
		return leftOperandValue == VARIABLE_CIBLE && returnValue.value == VALEUR_CIBLE;
	}

	@Override
	public void condOnVariable(int leftOperandValue, Operator operatorValue, ReturnValue returnValue) {
		if (leftOperandValue == VARIABLE_CIBLE && returnValue.value == VALEUR_CIBLE) {
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
	public void changeVariable(SwitchNumber variable, Operator operator, ReturnValue returnValue) {
		Valeur value = getValue(returnValue);
		
		if (operator == Operator.AFFECTATION) {
			variableUtilisee.put(variable.numberDebut, value);
		} else {
			String symbole = getSymbole(operator);
			
			variableUtilisee.put(variable.numberDebut, new Calcul(variableUtilisee.get(variable.numberDebut), symbole, value));
		}
	}

	@Override
	public void callMapEvent(int eventNumber, int eventPage) {
		if (eventNumber == TERMINATOR_EVENT_MAP_NUMB && eventPage == TERMINATOR_EVENT_MAP_PAGE) {
			fixerLaSortie();
		}
	}


}
