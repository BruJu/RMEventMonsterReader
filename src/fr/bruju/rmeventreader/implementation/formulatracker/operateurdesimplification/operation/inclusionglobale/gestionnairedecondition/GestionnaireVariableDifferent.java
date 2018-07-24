package fr.bruju.rmeventreader.implementation.formulatracker.operateurdesimplification.operation.inclusionglobale.gestionnairedecondition;

import fr.bruju.rmeventreader.actionmakers.actionner.Operator;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.condition.CFixe;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.condition.CVariable;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.condition.Condition;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.valeur.VConstante;
import fr.bruju.rmeventreader.implementation.formulatracker.operateurdesimplification.operation.inclusionglobale.Integreur;
import fr.bruju.rmeventreader.implementation.formulatracker.simplification.EvaluateurSimple;

public class GestionnaireVariableDifferent implements GestionnaireDeCondition {

	private Integreur integreur;
	private CVariable base;
	private int maDroite;
	
	private EvaluateurSimple eval;

	public GestionnaireVariableDifferent(Integreur integreur, CVariable cVariable) {
		this.integreur = integreur;
		this.base = cVariable;
		eval = new EvaluateurSimple();
		maDroite = eval.evaluer(base.droite);
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
		
		if (cond.operateur != Operator.IDENTIQUE || cond.operateur != Operator.DIFFERENT) {
			integreur.refuse(cond);
			return cond;
		}
		
		
		int saDroite = eval.evaluer(cond.droite);
		
		if (cond.operateur == Operator.IDENTIQUE) {
			integreur.gestionnairePush(null, !cond.operateur.test(maDroite, saDroite));

			return CFixe.get(!cond.operateur.test(maDroite, saDroite));
		}
		
		if (cond.operateur == Operator.DIFFERENT) {
			if (maDroite == saDroite) {
				integreur.gestionnairePush(null, true);

				return CFixe.get(true);
			}
		}

		integreur.refuse(cond);
		return cond;
	}

}
