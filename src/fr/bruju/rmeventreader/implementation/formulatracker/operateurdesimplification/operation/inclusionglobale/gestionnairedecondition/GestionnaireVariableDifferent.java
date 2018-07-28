package fr.bruju.rmeventreader.implementation.formulatracker.operateurdesimplification.operation.inclusionglobale.gestionnairedecondition;

import fr.bruju.rmeventreader.actionmakers.actionner.Operator;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.condition.CFixe;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.condition.CVariable;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.condition.Condition;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.valeur.VConstante;

public class GestionnaireVariableDifferent implements GestionnaireDeCondition {

	private CVariable base;
	private int maDroite;
	

	public GestionnaireVariableDifferent(CVariable cVariable) {
		this.base = cVariable;
		maDroite = VConstante.evaluer(base.droite);
	}

	@Override
	public Condition conditionVariable(CVariable cond) {
		if(!(base.gauche.equals(cond.gauche)
				&& cond.droite instanceof VConstante)) {
			return cond;
		}
		
		// TODO : si on a x != 4 et qu'on voit x <= 4, on devrait push x < 4
		
		if (cond.operateur != Operator.IDENTIQUE || cond.operateur != Operator.DIFFERENT) {
			return cond;
		}
		
		
		int saDroite = VConstante.evaluer(cond.droite);
		
		if (cond.operateur == Operator.IDENTIQUE) {
			return CFixe.get(!cond.operateur.test(maDroite, saDroite));
		}
		
		if (cond.operateur == Operator.DIFFERENT) {
			if (maDroite == saDroite) {
				return CFixe.get(true);
			}
		}

		return cond;
	}

}
