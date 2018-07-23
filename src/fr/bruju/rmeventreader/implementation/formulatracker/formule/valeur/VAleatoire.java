package fr.bruju.rmeventreader.implementation.formulatracker.formule.valeur;

import fr.bruju.rmeventreader.implementation.formulatracker.simplification.VisiteurDeComposants;
import java.util.Objects;

public class VAleatoire implements Valeur {
	public final int min;
	public final int max;
	
	
	public VAleatoire(int min, int max) {
		this.min = min;
		this.max = max;
	}


	@Override
	public String getString() {
		return min + "~" + max;
	}

	@Override
	public void accept(VisiteurDeComposants visiteurDeComposant) {
		visiteurDeComposant.visit(this);
	}


	@Override
	public boolean equals(final Object other) {
		if (!(other instanceof VAleatoire)) {
			return false;
		}
		VAleatoire castOther = (VAleatoire) other;
		return Objects.equals(min, castOther.min) && Objects.equals(max, castOther.max);
	}


	@Override
	public int hashCode() {
		return Objects.hash(min, max);
	}


}
