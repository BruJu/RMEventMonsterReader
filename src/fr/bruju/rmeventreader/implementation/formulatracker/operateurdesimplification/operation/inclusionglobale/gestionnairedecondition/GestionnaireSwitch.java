package fr.bruju.rmeventreader.implementation.formulatracker.operateurdesimplification.operation.inclusionglobale.gestionnairedecondition;

import fr.bruju.rmeventreader.implementation.formulatracker.composant.condition.CSwitch;
import fr.bruju.rmeventreader.implementation.formulatracker.operateurdesimplification.operation.inclusionglobale.Integreur;
import fr.bruju.rmeventreader.utilitaire.Pair;

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
	public Pair<CSwitch, Boolean> conditionSwitch(CSwitch cond) {
		if (!base.interrupteur.equals(cond.interrupteur)) {
			integreur.refuse(cond);
			return new Pair<>(cond, null);
		}
		
		integreur.gestionnairePush(null, base.interrupteur == cond.interrupteur);
		return new Pair<>(null, base.interrupteur == cond.interrupteur);
	}

}
