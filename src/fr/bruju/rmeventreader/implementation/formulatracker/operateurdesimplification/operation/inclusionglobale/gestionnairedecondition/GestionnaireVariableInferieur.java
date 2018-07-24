package fr.bruju.rmeventreader.implementation.formulatracker.operateurdesimplification.operation.inclusionglobale.gestionnairedecondition;

import fr.bruju.rmeventreader.actionmakers.actionner.Operator;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.condition.CFixe;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.condition.CVariable;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.condition.Condition;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.valeur.VConstante;
import fr.bruju.rmeventreader.implementation.formulatracker.operateurdesimplification.operation.inclusionglobale.Integreur;
import fr.bruju.rmeventreader.implementation.formulatracker.simplification.EvaluateurSimple;

public class GestionnaireVariableInferieur implements GestionnaireDeCondition {

	private Integreur integreur;
	private CVariable base;
	
	private Operator op;
	private int maDroite;
	
	private EvaluateurSimple eval;

	public GestionnaireVariableInferieur(Integreur integreur, CVariable cVariable, boolean b) {
		this.integreur = integreur;
		this.base = cVariable;
		eval = new EvaluateurSimple();
		maDroite = eval.evaluer(base.droite);
		
		op = Operator.INFEGAL;
		
		if (!b) {
			maDroite = maDroite - 1;
		}
	}

	@Override
	public Integreur getIntegreur() {
		return integreur;
	}
	
	@Override
	public Condition conditionVariable(CVariable cond) {
		if(!(base.gauche == cond.gauche
				&& cond.droite instanceof VConstante)) {
			integreur.refuse(cond);
			return cond;
		}
		
		// TODO : si on a x != 4 et qu'on voit x <= 4, on devrait push x < 4
		
		int saDroite = eval.evaluer(cond.droite);
		
		if (cond.operateur == Operator.IDENTIQUE) {
			boolean r = op.test(saDroite, maDroite);
			
			integreur.gestionnairePush(null, r);
			return CFixe.get(r);
		}
		
		if (cond.operateur == Operator.DIFFERENT) {
			integreur.refuse(cond);
			return cond;
		}
		
		if (cond.operateur == Operator.INF || cond.operateur == Operator.INFEGAL) {
			if (cond.operateur == Operator.INF) {
				saDroite--;
			}
			
			boolean r = op.test(maDroite, saDroite);
			
			if (r) {
				integreur.gestionnairePush(null, true);
				return CFixe.get(true);
			} else {
				integreur.refuse(cond);
				return cond;
			}
		}
		
		if (cond.operateur == Operator.SUP || cond.operateur == Operator.SUPEGAL) {
			if (cond.operateur == Operator.SUPEGAL) {
				saDroite--;
			}
			
			boolean r = Operator.SUP.test(saDroite, maDroite);
			
			if (!r) {
				integreur.gestionnairePush(null, false);
				return CFixe.get(false);
			} else {
				integreur.refuse(cond);
				return cond;
			}
		}

		integreur.refuse(cond);
		return cond;
	}

}
