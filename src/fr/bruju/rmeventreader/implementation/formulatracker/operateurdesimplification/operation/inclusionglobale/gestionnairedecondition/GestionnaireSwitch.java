package fr.bruju.rmeventreader.implementation.formulatracker.operateurdesimplification.operation.inclusionglobale.gestionnairedecondition;

import fr.bruju.rmeventreader.implementation.formulatracker.composant.condition.CFixe;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.condition.CSwitch;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.condition.Condition;
import fr.bruju.rmeventreader.implementation.formulatracker.operateurdesimplification.operation.inclusionglobale.Integreur;

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
	public Condition conditionSwitch(CSwitch cond) {
		if (!base.interrupteur.equals(cond.interrupteur)) {
			integreur.refuse(cond);
			return cond;
		}
		
		integreur.gestionnairePush(null, base.interrupteur == cond.interrupteur);
		return CFixe.get(base.interrupteur == cond.interrupteur);
	}

}
