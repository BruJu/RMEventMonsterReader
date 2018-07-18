package fr.bruju.rmeventreader.implementation.formulareader.actionmaker;

import java.util.ArrayList;
import java.util.List;

import fr.bruju.rmeventreader.actionmakers.actionner.ActionMakerDefalse;
import fr.bruju.rmeventreader.actionmakers.actionner.Operator;
import fr.bruju.rmeventreader.actionmakers.donnees.ValeurAleatoire;
import fr.bruju.rmeventreader.actionmakers.donnees.ValeurFixe;
import fr.bruju.rmeventreader.actionmakers.donnees.Variable;
import fr.bruju.rmeventreader.implementation.formulareader.formule.Condition;
import fr.bruju.rmeventreader.implementation.formulareader.formule.ConditionArme;
import fr.bruju.rmeventreader.implementation.formulareader.formule.ConditionSwitch;
import fr.bruju.rmeventreader.implementation.formulareader.formule.ConditionVariable;
import fr.bruju.rmeventreader.implementation.formulareader.formule.NonEvaluableException;
import fr.bruju.rmeventreader.implementation.formulareader.formule.DependantDeStatistiquesEvaluation;
import fr.bruju.rmeventreader.implementation.formulareader.formule.NewValeur;
import fr.bruju.rmeventreader.implementation.formulareader.formule.Valeur;
import fr.bruju.rmeventreader.utilitaire.Pair;

public class FormulaCalculator implements ActionMakerDefalse {
	/* ===========================
	 * Constantes liées au dataset
	 * =========================== */

	/* =========
	 * Attributs
	 * ========= */
	private Etat etat; // Etat de la mémoire 
	private List<Valeur> sortie; // Sorties possibles

	private Pile pile; // Pile de conditions
	private ConstructionBorne construireBorne; // Construction de la borne

	/* ============
	 * Constructeur
	 * ============ */

	public FormulaCalculator() {
		etat = new Etat("ressources/CalculFormule.txt");
		sortie = new ArrayList<>();
		pile = new Pile();
	}

	/* =======
	 * Sorties
	 * ======= */

	public List<Valeur> getSortie() {
		return sortie;
	}

	/* ==================
	 * Conditions stables
	 * ================== */

	// Switch

	@Override
	public boolean condOnSwitch(int number, boolean value) {
		if (pile.possedeUnFaux()) {
			return false;
		}

		Valeur v = etat.getSwitch(number);

		Condition cond = new ConditionSwitch(v, value);

		boolean resultat;

		try {
			resultat = cond.tester();
		} catch (NonEvaluableException | DependantDeStatistiquesEvaluation e) {
			entrerDansUnEtatFils(cond);
			return true;
		}

		if (resultat) {
			this.pile.empiler(Pile.Valeur.VRAI);
		} else {
			this.pile.empiler(Pile.Valeur.FAUX);
		}

		return true;
	}

	private void entrerDansUnEtatFils(Condition cond) {
		pile.empiler(Pile.Valeur.EXPLOREBOTH);
		Pair<Etat, Etat> etatsFils = etat.creerFils(cond);

		this.etat = etatsFils.getLeft();

	}

	@Override
	public boolean condOnEquippedItem(int heroId, int itemId) {
		if (pile.possedeUnFaux()) {
			return false;
		}

		entrerDansUnEtatFils(new ConditionArme(heroId, itemId));
		return true;
	}

	@Override
	public void condElse() {
		if (this.construireBorne != null) {
			this.construireBorne.tuer();
		} else {
			pile.inverserSommet();

			if (pile.sommetExplore()) {
				etat = etat.getPere().getFilsDroit();
			}
		}
	}

	@Override
	public void condEnd() {
		if (this.construireBorne == null) {
			if (pile.sommetExplore()) {
				etat = etat.getPere();

				etat.unifierFils();
			}

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

	/* ====================
	 * Conditions instables
	 * ==================== */

	// Variable

	@Override
	public boolean condOnVariable(int leftOperandValue, Operator operatorValue, ValeurFixe returnValue) {
		if (pile.possedeUnFaux()) {
			return false;
		}

		if (this.construireBorne != null) {
			construireBorne.tuer();

		}

		if (showNextCond) {
			showNextCond = false;

			// System.out.println("SHOWNEXTCOND " + leftOperandValue + operatorValue + returnValue.get());

			if (leftOperandValue == 519 && operatorValue == Operator.SUP && returnValue.get() == 0) {
				pile.empiler(Pile.Valeur.VRAI);
				return true;
			}
		}

		Valeur valeurCible = etat.getValeur(leftOperandValue);

		try {
			int evaluation = valeurCible.evaluer();

			boolean resultatTest = operatorValue.test(evaluation, returnValue.get());
			pile.empiler(resultatTest ? Pile.Valeur.VRAI : Pile.Valeur.FAUX);

		} catch (NonEvaluableException e) {
			Condition cond = new ConditionVariable(valeurCible, operatorValue, NewValeur.Numerique(returnValue.get()));
			entrerDansUnEtatFils(cond);
			return true;
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
			/*
			if (valeurCible.estGarantieDeLaFormeMPMoinsConstante() && operatorValue == Operator.INF
					&& returnValue.get() == 0) {
				return false;
			}
*/
			// Connaissance Metier 3 : On peut être borné

			if (this.construireBorne == null) {
				this.construireBorne = new ConstructionBorne(leftOperandValue, valeurCible, operatorValue,
						returnValue.get());

				return true;
			}

			Condition cond = new ConditionVariable(valeurCible, operatorValue, NewValeur.Numerique(returnValue.get()));
			entrerDansUnEtatFils(cond);
			return true;

			/*
			System.out.println("Stat dep  : " + valeurCible.getString() + " = " + leftOperandValue + " " + operatorValue
					+ " " + returnValue.get());
			*/
		}

		return true;
	}

	// Else End

	/* ==========
	 * Actions
	 * ========== */

	@Override
	public void changeVariable(Variable variable, Operator operator, ValeurFixe returnValue) {
		if (pile.possedeUnFaux()) {
			return;
		}

		if (construireBorne != null)
			construireBorne.changeVariable(variable.get(), operator, Traduction.getValue(returnValue));
		else
			chgVar(variable.get(), operator, Traduction.getValue(returnValue));
	}

	@Override
	public void changeVariable(Variable variable, Operator operator, ValeurAleatoire returnValue) {
		if (pile.possedeUnFaux()) {
			return;
		}

		if (construireBorne != null)
			construireBorne.changeVariable(variable.get(), operator, Traduction.getValue(returnValue));
		else
			chgVar(variable.get(), operator, Traduction.getValue(returnValue));
	}

	@Override
	public void changeVariable(Variable variable, Operator operator, Variable returnValue) {
		if (pile.possedeUnFaux()) {
			return;
		}

		if (construireBorne != null)
			construireBorne.tuer();
		else
			chgVar(variable.get(), operator, Traduction.getValue(etat, returnValue));
	}

	private void chgVar(int idVariableAModifier, Operator operator, Valeur rightValue) {
		if (pile.possedeUnFaux()) {
			return;
		}

		if (operator == Operator.AFFECTATION) {
			etat.setValue(idVariableAModifier, rightValue);
		} else {

			if (operator == Operator.MINUS /*|| operator == Operator.PLUS*/) {
				if (etat.estUneSortie(idVariableAModifier)) {
					sortie.add(rightValue);
				}
			}

			String symbole = Traduction.getSymbole(operator);

			Valeur calcul = NewValeur.Calcul(etat.getValeur(idVariableAModifier), symbole, rightValue);

			etat.setValue(idVariableAModifier, calcul);
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
