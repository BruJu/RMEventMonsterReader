package fr.bruju.rmeventreader.implementation.formulareader.actionmaker;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import fr.bruju.rmeventreader.actionmakers.actionner.ActionMakerDefalse;
import fr.bruju.rmeventreader.actionmakers.actionner.Operator;
import fr.bruju.rmeventreader.actionmakers.decrypter.Recognizer;
import fr.bruju.rmeventreader.actionmakers.donnees.ValeurAleatoire;
import fr.bruju.rmeventreader.actionmakers.donnees.ValeurFixe;
import fr.bruju.rmeventreader.actionmakers.donnees.Variable;
import fr.bruju.rmeventreader.filereader.FileReaderByLine;
import fr.bruju.rmeventreader.implementation.formulareader.formule.Calcul;
import fr.bruju.rmeventreader.implementation.formulareader.formule.NonEvaluableException;
import fr.bruju.rmeventreader.implementation.formulareader.formule.DependantDeStatistiquesEvaluation;
import fr.bruju.rmeventreader.implementation.formulareader.formule.Valeur;
import fr.bruju.rmeventreader.implementation.formulareader.formule.ValeurNumerique;
import fr.bruju.rmeventreader.utilitaire.Ensemble;

public class FormulaCalculator implements ActionMakerDefalse {
	private static final int TERMINATOR_EVENT_MAP_NUMB = 77;
	private static final int TERMINATOR_EVENT_MAP_PAGE = 1;
	
	private static final int[] ENTER_IN_SWITCHES = {181, 182};
	
	private static final int[] VARIABLE_DEGATS_INFLIGES = {1181, 548};
	
	private Etat etat;

	private List<Valeur> sortie;
	
	private Pile pile = new Pile();
	private ConstructionBorne construireBorne;
	
	@Override
	public boolean condOnEquippedItem(int heroId, int itemId) {
		pile.empiler(Pile.Valeur.FAUX);
		
		return true;
	}

	
	public FormulaCalculator() {
		etat = new Etat();
		sortie = new ArrayList<>();
		
		try {
			FileReaderByLine.lireLeFichier(new File("ressources/CalculFormule.txt"),
					ligne -> {
						List<String> args = Recognizer.tryPattern("_ _", ligne);
						
						int idVar = Integer.parseInt(args.get(0));
						int valFixee = Integer.parseInt(args.get(1));
						
						etat.enregistrerValeurDepart(idVar, valFixee);
					});
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	/*
	 * Sortie
	 */
	
	private void fixerLaSortie() {
		sortie.add(etat.getSortie(VARIABLE_DEGATS_INFLIGES));
	}
	
	public Valeur getSortie() {
		if (sortie.isEmpty()) {
			return null;
		}
		
		return sortie.get(0);
	}
	
	/*
	 * Valeurs selon entree de actionmaker
	 */
	
	private ValeurNumerique getValue(ValeurFixe value) {
		return new ValeurNumerique(value.get());
	}

	private ValeurNumerique getValue(ValeurAleatoire value) {
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
		if (pile.possedeUnFaux()) {
			return false;
		}
		
		return Ensemble.appartient(number, ENTER_IN_SWITCHES);
	}
	
	
	// Variable

	@Override
	public boolean condOnVariable(int leftOperandValue, Operator operatorValue, ValeurFixe returnValue) {
		if (pile.possedeUnFaux()) {
			return false;
		}
		
		if (showNextCond) {
			showNextCond = false;
			
			// System.out.println("SHOWNEXTCOND " + leftOperandValue + operatorValue + returnValue.get());
			
			if (leftOperandValue == 519 && operatorValue == Operator.SUP && returnValue.get() == 0) {
				return true;
			}
		}
		
		Valeur valeurCible = etat.getValeur(leftOperandValue);
		
		try {
			int evaluation = valeurCible.evaluer();
			
			boolean resultatTest = operatorValue.test(evaluation, returnValue.get());
			pile.empiler(resultatTest ? Pile.Valeur.VRAI : Pile.Valeur.FAUX);
			
		} catch (NonEvaluableException e) {
			System.out.println("Cant eval : "
				+ valeurCible.getString() + " = "
				+ leftOperandValue + " "
				+ operatorValue + " "
				+ returnValue.get());
			
			return false;
		} catch (DependantDeStatistiquesEvaluation e) {
			
			
			
			// Connaissance Metier 1 : Les statistique sont toujours positives
			if (operatorValue == Operator.SUP && returnValue.get() == 0) {
				if (valeurCible.estGarantiePositive()) {
					pile.empiler(Pile.Valeur.VRAI);
					return true;
				}
			}
			if (operatorValue == Operator.INFEGAL && returnValue.get() == 0) {
				if (valeurCible.estGarantiePositive()) {
					pile.empiler(Pile.Valeur.FAUX);
					return true;
				}
			}
			
			// Connaissance Metier 2 : Les conditions de la forme "MP - constante < 0" ne nous interessent pas
			if (valeurCible.estGarantieDeLaFormeMPMoinsConstante() && operatorValue == Operator.INF && returnValue.get() == 0) {
				return false;
			}
			
			// Connaissance Metier 3 : On peut �tre born�

			
			if (this.construireBorne == null) {
				this.construireBorne = new ConstructionBorne(leftOperandValue, valeurCible, operatorValue, returnValue.get());
				
				return true;
			}

			System.out.println("Stat dep  : " + valeurCible.getString() + " = " + leftOperandValue + " " + operatorValue + " " + returnValue.get());
			
			return false;
		}
		
		
		
		
		return true;
	}


	// Else End
	
	@Override
	public void condElse() {
		if (this.construireBorne != null) {
			this.construireBorne.tuer();
		} else {
			pile.inverserSommet();
		}
	}

	@Override
	public void condEnd() {
		if (this.construireBorne == null) {
			pile.depiler();
		} else {
			Valeur val = this.construireBorne.finir();
			
			if (val == null) {
				this.construireBorne = null;
				return;
			}
			
			etat.setValue(construireBorne.getVariable(), val);
			this.construireBorne = null;
			
		}
	}

	/* ==========
	 * Actions
	 * ========== */

	@Override
	public void changeVariable(Variable variable, Operator operator, ValeurFixe returnValue) {
		if (pile.possedeUnFaux()) {
			return;
		}
		
		if (construireBorne != null)
			construireBorne.changeVariable(variable.get(), operator, getValue(returnValue));
		else
			chgVar(variable.get(), operator, getValue(returnValue));
	}

	@Override
	public void changeVariable(Variable variable, Operator operator, ValeurAleatoire returnValue) {
		if (pile.possedeUnFaux()) {
			return;
		}

		if (construireBorne != null)
			construireBorne.changeVariable(variable.get(), operator, getValue(returnValue));
		else
			chgVar(variable.get(), operator, getValue(returnValue));
	}
	
	@Override
	public void changeVariable(Variable variable, Operator operator, Variable returnValue) {
		if (pile.possedeUnFaux()) {
			return;
		}
		
		if (construireBorne != null)
			construireBorne.tuer();
		else
			chgVar(variable.get(), operator, getValue(returnValue));
	}

	private void chgVar(int idVariableAModifier, Operator operator, Valeur rightValue) {
		if (pile.possedeUnFaux()) {
			return;
		}
		
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
		if (pile.possedeUnFaux()) {
			return;
		}
		
		if (eventNumber == TERMINATOR_EVENT_MAP_NUMB && eventPage == TERMINATOR_EVENT_MAP_PAGE) {
			
			fixerLaSortie();
		}
	}
	
	@Override
	public void getComment(String str) {
		if (pile.possedeUnFaux()) {
			return;
		}
		
		if (str.equals("CALCUL DEGATS")) {
			showNextCond = true;
		}
	}

	private boolean showNextCond = false;
}
