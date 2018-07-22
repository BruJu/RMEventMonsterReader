package fr.bruju.rmeventreader.implementation.formulatracker.formule.condition;

import fr.bruju.rmeventreader.actionmakers.actionner.Operator;
import fr.bruju.rmeventreader.implementation.formulatracker.formule.valeur.Valeur;

public class CVariable implements Condition {

	public CVariable(Valeur gauche, Operator operateur, Valeur vDroite) {
	}

	@Override
	public Condition revert() {
		return null;
	}

}
