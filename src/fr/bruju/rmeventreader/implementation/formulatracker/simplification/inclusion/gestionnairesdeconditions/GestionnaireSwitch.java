package fr.bruju.rmeventreader.implementation.formulatracker.simplification.inclusion.gestionnairesdeconditions;

import fr.bruju.rmeventreader.implementation.formulatracker.formule.condition.CSwitch;
import fr.bruju.rmeventreader.implementation.formulatracker.simplification.inclusion.IntegreurDeCondition;

public class GestionnaireSwitch implements GestionnaireDeCondition {

	private IntegreurDeCondition integreur;
	private CSwitch base;

	public GestionnaireSwitch(IntegreurDeCondition integreur, CSwitch cSwitch) {
		this.integreur = integreur;
		this.base = cSwitch;
	}

	@Override
	public IntegreurDeCondition getIntegreur() {
		return integreur;
	}

	@Override
	public void conditionSwitch(CSwitch cond) {
		if (!base.interrupteur.equals(cond.interrupteur)) {
			integreur.refuse(cond);
			return;
		}
		
		integreur.gestionnairePush(null, base.interrupteur == cond.interrupteur);
	}

}
