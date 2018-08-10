package fr.bruju.rmeventreader.implementation.recomposeur.operations.desinjection;

import fr.bruju.rmeventreader.implementation.recomposeur.formulededegats.ConditionAffichable;
import java.util.Objects;

public class Ciblage implements ConditionAffichable {
	public final boolean estCible;

	public Ciblage(boolean estCible) {
		this.estCible = estCible;
	}

	@Override
	public String getString() {
		return estCible ? "Ciblé" : "AoE";
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(estCible);
	}

	@Override
	public boolean equals(Object object) {
		if (object instanceof Ciblage) {
			Ciblage that = (Ciblage) object;
			return this.estCible == that.estCible;
		}
		return false;
	}
}