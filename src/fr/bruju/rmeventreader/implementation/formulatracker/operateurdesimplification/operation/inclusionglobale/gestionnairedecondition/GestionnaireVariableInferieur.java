package fr.bruju.rmeventreader.implementation.formulatracker.operateurdesimplification.operation.inclusionglobale.gestionnairedecondition;

import fr.bruju.rmeventreader.actionmakers.actionner.Operator;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.condition.CVariable;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.valeur.VConstante;
import fr.bruju.rmeventreader.implementation.formulatracker.operateurdesimplification.operation.inclusionglobale.Integreur;
import fr.bruju.rmeventreader.implementation.formulatracker.simplification.EvaluateurSimple;
import fr.bruju.rmeventreader.utilitaire.Pair;

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
	public Pair<CVariable, Boolean> conditionVariable(CVariable cond) {
		if(!(base.gauche == cond.gauche
				&& cond.droite instanceof VConstante)) {
			integreur.refuse(cond);
			return new Pair<>(cond, null);
		}
		
		// TODO : si on a x != 4 et qu'on voit x <= 4, on devrait push x < 4
		
		int saDroite = eval.evaluer(cond.droite);
		
		if (cond.operateur == Operator.IDENTIQUE) {
			boolean r = op.test(saDroite, maDroite);
			
			integreur.gestionnairePush(null, r);
			return new Pair<>(null, r);
		}
		
		if (cond.operateur == Operator.DIFFERENT) {
			integreur.refuse(cond);
			return new Pair<>(cond, null);
		}
		
		if (cond.operateur == Operator.INF || cond.operateur == Operator.INFEGAL) {
			if (cond.operateur == Operator.INF) {
				saDroite--;
			}
			
			boolean r = op.test(maDroite, saDroite);
			
			if (r) {
				integreur.gestionnairePush(null, true);
				return new Pair<>(null, true);
			} else {
				integreur.refuse(cond);
				return new Pair<>(cond, null);
			}
		}
		
		if (cond.operateur == Operator.SUP || cond.operateur == Operator.SUPEGAL) {
			if (cond.operateur == Operator.SUPEGAL) {
				saDroite--;
			}
			
			boolean r = Operator.SUP.test(saDroite, maDroite);
			
			if (!r) {
				integreur.gestionnairePush(null, false);
				return new Pair<>(null, false);
			} else {
				integreur.refuse(cond);
				return new Pair<>(cond, null);
			}
		}

		integreur.refuse(cond);
		return new Pair<>(cond, null);
	}

}
