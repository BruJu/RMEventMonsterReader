package fr.bruju.rmeventreader.implementation.formulatracker.simplification.inclusion.gestionnairesdeconditions;

import fr.bruju.rmeventreader.actionmakers.actionner.Operator;
import fr.bruju.rmeventreader.implementation.formulatracker.formule.condition.CVariable;
import fr.bruju.rmeventreader.implementation.formulatracker.formule.valeur.VConstante;
import fr.bruju.rmeventreader.implementation.formulatracker.simplification.EvaluateurSimple;
import fr.bruju.rmeventreader.implementation.formulatracker.simplification.inclusion.IntegreurDeCondition;

public class GestionnaireVariableDifferent implements GestionnaireDeCondition {

	private IntegreurDeCondition integreur;
	private CVariable base;
	private int maDroite;
	
	private EvaluateurSimple eval;

	public GestionnaireVariableDifferent(IntegreurDeCondition integreur, CVariable cVariable) {
		this.integreur = integreur;
		this.base = cVariable;
		eval = new EvaluateurSimple();
		maDroite = eval.evaluer(base.droite);
	}

	@Override
	public IntegreurDeCondition getIntegreur() {
		return integreur;
	}
	
	@Override
	public void conditionVariable(CVariable cond) {
		if(!(base.gauche == cond.gauche
				&& cond.droite instanceof VConstante)) {
			integreur.refuse(cond);
			return;
		}
		
		// TODO : si on a x != 4 et qu'on voit x <= 4, on devrait push x < 4
		
		if (cond.operateur != Operator.IDENTIQUE || cond.operateur != Operator.DIFFERENT) {
			integreur.refuse(cond);
			return;
		}
		
		
		int saDroite = eval.evaluer(cond.droite);
		
		if (cond.operateur == Operator.IDENTIQUE) {
			integreur.gestionnairePush(null, !cond.operateur.test(maDroite, saDroite));
			return;
		}
		
		if (cond.operateur == Operator.DIFFERENT) {
			if (maDroite == saDroite) {
				integreur.gestionnairePush(null, true);
				return;
			}
		}

		integreur.refuse(cond);
	}

}
