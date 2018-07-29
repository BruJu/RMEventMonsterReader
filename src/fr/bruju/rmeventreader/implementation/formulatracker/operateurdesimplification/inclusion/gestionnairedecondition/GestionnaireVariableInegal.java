package fr.bruju.rmeventreader.implementation.formulatracker.operateurdesimplification.inclusion.gestionnairedecondition;

import fr.bruju.rmeventreader.actionmakers.actionner.Operator;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.condition.CFixe;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.condition.CVariable;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.condition.Condition;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.valeur.VConstante;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.valeur.Valeur;
import fr.bruju.rmeventreader.utilitaire.Pair;

/**
 * Gestionnaire de conditions avec une base inégale
 * @author Bruju
 *
 */
public class GestionnaireVariableInegal implements GestionnaireDeCondition {
	// Conditions de type x • constante
	private Valeur base;
	private Operator op;
	private int maDroite;

	/**
	 * Crée un gestionnaire de conditions pour la condition d'inégalité donnée
	 * @param cVariable La condition sur laquelle construire le gestionnaire
	 */
	public GestionnaireVariableInegal(CVariable cVariable) {
		this.base = cVariable.gauche;

		
		Pair<Operator, Integer> e = evaluerSansBorne(cVariable);
		op = e.getLeft();
		maDroite = e.getRight();
		
		op = cVariable.operateur;

		Integer md = VConstante.evaluer(cVariable.droite);

		maDroite = md;
	}

	/**
	 * Transforme la condition en une condition sans inégalités contenant un égal et donne la valeur de sa partie droite
	 * @param cVariable La condition
	 * @return Une paire opérateur et évaluation de la partie droite
	 */
	private Pair<Operator, Integer> evaluerSansBorne(CVariable cVariable) {
		Integer evaluation = VConstante.evaluer(cVariable.droite);
		
		if (evaluation == null)
			return null;
		
		Operator operateur = cVariable.operateur;
		
		if (operateur == Operator.INFEGAL) {
			// x <= 3 équivaut = x < 4
			evaluation ++;
			operateur = Operator.INF;
		}
		if (operateur == Operator.SUPEGAL) {
			// x >= 3 équivaut à x > 2
			evaluation --;
			operateur = Operator.SUP;
		}
		
		return new Pair<>(operateur, evaluation);
	}

	@Override
	public Condition conditionVariable(CVariable cond) {
		if (!(base.equals(cond.gauche))) {
			return cond;
		}
		
		/*
		 * DIFFERENT
		 */
		if (cond.operateur == Operator.DIFFERENT) {
			return cond;
		}
		
		Pair<Operator, Integer> autreEv = evaluerSansBorne(cond);
		
		if (autreEv == null) {
			return cond;
		}
		
		Operator autreOp = autreEv.getLeft();
		int saDroite = autreEv.getRight();

		/*
		 * IDENTIQUE
		 */
		if (autreOp == Operator.IDENTIQUE) {
			if (op.test(saDroite, maDroite)) {
				// Est vérifiable, mais pas toujours
				return cond;
			} else {
				// N'est jamais vérifiée
				return CFixe.get(false);
			}
		}
		
		/*
		 * OPERATEURS INEGAUX
		 */
		
		if (op == autreOp) {
			if (op.test(saDroite, maDroite)) {
				return cond;
			} else {
				return CFixe.get(true);
			}
		} else {
			// pour inférieur
			// on veut qu'il y ait au moins un x tel que ma droite < x < sa droite
			// <=> sa droite - ma droite >= 2
			if (op == Operator.INF) {
				if (saDroite - maDroite >= 2) {
					return cond;
				} else {
					return CFixe.get(false);
				}
			}
			
			
			// pour supérieur
			// on veut au moins un x tel que sadroite < x < madroite
			// <=> madroite - sadroite >= 2
			if (op == Operator.SUP) {
				if (-saDroite + maDroite >= 2) {
					return cond;
				} else {
					return CFixe.get(false);
				}
			}
		}

		return cond;
	}

}
