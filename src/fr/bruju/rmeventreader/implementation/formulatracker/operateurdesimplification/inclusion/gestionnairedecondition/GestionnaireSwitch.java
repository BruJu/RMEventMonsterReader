package fr.bruju.rmeventreader.implementation.formulatracker.operateurdesimplification.inclusion.gestionnairedecondition;

import fr.bruju.rmeventreader.implementation.formulatracker.composant.condition.CFixe;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.condition.CSwitch;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.condition.Condition;

public class GestionnaireSwitch implements GestionnaireDeCondition {

	private CSwitch base;

	public GestionnaireSwitch(CSwitch cSwitch) {
		this.base = cSwitch;
	}

	@Override
	public Condition conditionSwitch(CSwitch cond) {
		if (!base.interrupteur.equals(cond.interrupteur)) {
			return cond;
		}
		
		return CFixe.get(base.valeur == cond.valeur);
	}

}
