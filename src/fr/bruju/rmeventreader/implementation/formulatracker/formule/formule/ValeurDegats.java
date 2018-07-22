package fr.bruju.rmeventreader.implementation.formulatracker.formule.formule;

import fr.bruju.rmeventreader.actionmakers.actionner.Operator;
import fr.bruju.rmeventreader.implementation.formulatracker.formule.valeur.Valeur;

public class ValeurDegats implements FormuleDeDegats {
	public final Operator operator;
	public final Valeur formule;

	public ValeurDegats(Operator operator, Valeur formule) {
		this.operator = operator;
		this.formule = formule;
	}




	@Override
	public String getString() {
		return null;
	}

}
