package fr.bruju.rmeventreader.implementation.formulatracker.formule.condition;

import fr.bruju.rmeventreader.implementation.formulatracker.formule.bouton.Bouton;

public class CSwitch implements Condition {

	public CSwitch(Bouton interrupteur, boolean valeur) {
	}

	@Override
	public Condition revert() {
		return null;
	}

}
