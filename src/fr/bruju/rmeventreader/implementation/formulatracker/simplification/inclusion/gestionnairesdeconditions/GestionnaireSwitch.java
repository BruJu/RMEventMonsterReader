package fr.bruju.rmeventreader.implementation.formulatracker.simplification.inclusion.gestionnairesdeconditions;

import fr.bruju.rmeventreader.implementation.formulatracker.composant.condition.CSwitch;
import fr.bruju.rmeventreader.implementation.formulatracker.simplification.inclusion.Integreur;

public class GestionnaireSwitch implements GestionnaireDeCondition {

	private Integreur integreur;
	private CSwitch base;

	public GestionnaireSwitch(Integreur integreur, CSwitch cSwitch) {
		this.integreur = integreur;
		this.base = cSwitch;
	}

	@Override
	public Integreur getIntegreur() {
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
