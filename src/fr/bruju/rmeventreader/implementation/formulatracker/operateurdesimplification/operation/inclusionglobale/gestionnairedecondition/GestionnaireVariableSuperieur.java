package fr.bruju.rmeventreader.implementation.formulatracker.operateurdesimplification.operation.inclusionglobale.gestionnairedecondition;

import fr.bruju.rmeventreader.actionmakers.actionner.Operator;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.condition.CFixe;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.condition.CVariable;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.condition.Condition;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.valeur.VConstante;
import fr.bruju.rmeventreader.implementation.formulatracker.simplification.EvaluateurSimple;

public class GestionnaireVariableSuperieur implements GestionnaireDeCondition {
	private CVariable base;
	
	private Operator op;
	private int maDroite;
	
	private EvaluateurSimple eval;

	public GestionnaireVariableSuperieur(CVariable cVariable, boolean b) {
		this.base = cVariable;
		
		eval = new EvaluateurSimple();
		maDroite = eval.evaluer(base.droite);
		
		op = Operator.SUPEGAL;
		
		if (!b) {
			maDroite = maDroite + 1;
		}
	}

	
	@Override
	public Condition conditionVariable(CVariable cond) {
		if(!(base.gauche.equals(cond.gauche)
				&& cond.droite instanceof VConstante)) {
			return cond;
		}
		
		// TODO : si on a x != 4 et qu'on voit x <= 4, on devrait push x < 4
		
		int saDroite = eval.evaluer(cond.droite);
		
		if (cond.operateur == Operator.IDENTIQUE) {
			boolean r = op.test(saDroite, maDroite);
			
			return CFixe.get(r);
		}
		
		if (cond.operateur == Operator.DIFFERENT) {
			return cond;
		}
		
		if (cond.operateur == Operator.SUP || cond.operateur == Operator.SUPEGAL) {
			if (cond.operateur == Operator.SUP) {
				saDroite++;
			}
			
			boolean r = op.test(maDroite, saDroite);
			
			if (r) {
				return CFixe.get(true);
			} else {
				return cond;
			}
		}
		
		if (cond.operateur == Operator.INF || cond.operateur == Operator.INFEGAL) {
			if (cond.operateur == Operator.INFEGAL) {
				saDroite++;
			}
			
			boolean r = Operator.INF.test(saDroite, maDroite);
			
			if (!r) {
				return CFixe.get(false);
			} else {
				return cond;
			}
		}

		return cond;
	}

}
