package fr.bruju.rmeventreader.implementation.formulatracker.formule.valeur;

import fr.bruju.rmeventreader.implementation.formulatracker.formule.ComposantTernaire;
import fr.bruju.rmeventreader.implementation.formulatracker.formule.condition.Condition;

public class VTernaire extends ComposantTernaire<Valeur> implements Valeur {
	public VTernaire(Condition condition, Valeur v1, Valeur v2) {
		super(condition, v1, v2);
	}
}
