package fr.bruju.rmeventreader.implementation.formulatracker.operateurdesimplification.operation.inclusionglobale.gestionnairedecondition;

import fr.bruju.rmeventreader.actionmakers.actionner.Operator;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.condition.CFixe;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.condition.CVariable;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.condition.Condition;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.visiteur.EvaluationRapide;
import fr.bruju.rmeventreader.utilitaire.Pair;

public class GestionnaireVariableInegal implements GestionnaireDeCondition {
	// Conditions de type x • constante
	private CVariable base;
	private Operator op;
	private int maDroite;

	private EvaluationRapide eval;

	public GestionnaireVariableInegal(CVariable cVariable) {
		this.base = cVariable;

		eval = EvaluationRapide.getInstance();
		
		Pair<Operator, Integer> e = evaluerSansBorne(cVariable);
		op = e.getLeft();
		maDroite = e.getRight();
		
		op = cVariable.operateur;

		Integer md = eval.evaluer(base.droite);

		if (md == null) {
			throw new RuntimeException("Inevaluable");
		}

		maDroite = md;
	}

	private Pair<Operator, Integer> evaluerSansBorne(CVariable cVariable) {
		Integer evaluation = eval.evaluer(cVariable.droite);
		
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
		if (!(base.gauche.equals(cond.gauche))) {
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
