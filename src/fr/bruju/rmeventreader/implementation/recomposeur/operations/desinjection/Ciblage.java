package fr.bruju.rmeventreader.implementation.recomposeur.operations.desinjection;

import fr.bruju.rmeventreader.implementation.recomposeur.formulededegats.ConditionAffichable;

public class Ciblage implements ConditionAffichable {
	public final boolean estCible;

	public Ciblage(boolean estCible) {
		this.estCible = estCible;
	}

	@Override
	public String getString() {
		return estCible ? "Ciblé" : "AoE";
	}
}