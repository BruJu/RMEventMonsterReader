package fr.bruju.rmeventreader.implementation.formulareader.actionmaker;

import fr.bruju.rmeventreader.actionmakers.actionner.ActionMakerDefalse;
import fr.bruju.rmeventreader.actionmakers.actionner.Operator;
import fr.bruju.rmeventreader.actionmakers.donnees.ValeurAleatoire;
import fr.bruju.rmeventreader.actionmakers.donnees.ValeurFixe;
import fr.bruju.rmeventreader.actionmakers.donnees.Variable;
import fr.bruju.rmeventreader.implementation.formulareader.formule.Calcul;
import fr.bruju.rmeventreader.implementation.formulareader.formule.CantEvaluateException;
import fr.bruju.rmeventreader.implementation.formulareader.formule.StatDependantEvaluation;
import fr.bruju.rmeventreader.implementation.formulareader.formule.Valeur;
import fr.bruju.rmeventreader.implementation.formulareader.formule.ValeurNumerique;
import fr.bruju.rmeventreader.utilitaire.Ensemble;

public class FormulaCalculator implements ActionMakerDefalse {
	private static final int TERMINATOR_EVENT_MAP_NUMB = 77;
	private static final int TERMINATOR_EVENT_MAP_PAGE = 1;
	
	private static final int[] ENTER_IN_SWITCHES = {181, 182};
	private static final int VARIABLE_CIBLE = 42;
	private static final int VALEUR_CIBLE = 70;
	
	private static final int VARIABLE_DEGATS_INFLIGES = 1181;
	private static final int VARIABLE_DEGATS_INFLIGES2 = 548;
	
	private Etat etat;
	
	private Valeur sortie;
	
	public FormulaCalculator() {
		etat = new Etat();
		etat.enregistrerValeurDepart(VARIABLE_CIBLE, VALEUR_CIBLE);
		etat.enregistrerValeurDepart(588, 0);
	}
	
	private PileDeBooleens pile = new PileDeBooleens();
	
	
	/*
	 * Sortie
	 */
	
	private void fixerLaSortie() {
		sortie = etat.getSortie(new int[]{VARIABLE_DEGATS_INFLIGES, VARIABLE_DEGATS_INFLIGES2});
	}
	
	public Valeur getSortie() {
		return sortie;
	}
	
	/*
	 * Valeurs selon entree de actionmaker
	 */
	
	private Valeur getValue(ValeurFixe value) {
		return new ValeurNumerique(value.get());
	}

	private Valeur getValue(ValeurAleatoire value) {
		return new ValeurNumerique(value.getMin(), value.getMax());
	}

	private Valeur getValue(Variable value) {
		int idVariable = value.get();
		
		return etat.getValeur(idVariable);
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
	public boolean condOnSwitch(int number, boolean value) {
		return Ensemble.appartient(number, ENTER_IN_SWITCHES);
	}
	
	
	// Variable

	@Override
	public boolean condOnVariable(int leftOperandValue, Operator operatorValue, ValeurFixe returnValue) {
		if (showNextCond) {
			showNextCond = false;
			
			// System.out.println("SHOWNEXTCOND " + leftOperandValue + operatorValue + returnValue.get());
			
			if (leftOperandValue == 519 && operatorValue == Operator.SUP && returnValue.get() == 0) {
				return true;
			}
		}
		
		Valeur valeurCible = etat.getValeur(leftOperandValue);
		
		try {
			int evaluation = valeurCible.evaluate();
			
			pile.empiler(operatorValue.test(evaluation, returnValue.get()));
		} catch (CantEvaluateException e) {
			
			
			return false;
		} catch (StatDependantEvaluation e) {
			
			return false;
		}
		
		
		
		
		return true;
	}


	// Else End
	
	@Override
	public void condElse() {
		pile.inverseSommet();
		
	}

	@Override
	public void condEnd() {
		pile.depiler();
		
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
			etat.setValue(idVariableAModifier, rightValue);
		} else {
			String symbole = getSymbole(operator);
			
			Calcul calcul = new Calcul(etat.getValeur(idVariableAModifier), symbole, rightValue);
			
			etat.setValue(idVariableAModifier, calcul);
		}
	}

	@Override
	public void callMapEvent(int eventNumber, int eventPage) {
		if (eventNumber == TERMINATOR_EVENT_MAP_NUMB && eventPage == TERMINATOR_EVENT_MAP_PAGE) {
			fixerLaSortie();
		}
	}
	
	@Override
	public void getComment(String str) {
		if (str.equals("CALCUL DEGATS")) {
			showNextCond = true;
		}
	}

	private boolean showNextCond = false;
}
