package fr.bruju.rmeventreader.implementation.formulatracker.formule.valeur;

import fr.bruju.rmeventreader.implementation.formulatracker.simplification.VisiteurDeComposants;
import java.util.Objects;

public class VConstante implements Valeur {
	public final int valeur;
	
	public VConstante(int valeur) {
		this.valeur = valeur;
	}

	@Override
	public String getString() {
		return Integer.toString(valeur);
	}


	@Override
	public void accept(VisiteurDeComposants visiteurDeComposant) {
		visiteurDeComposant.visit(this);
	}

	@Override
	public boolean equals(final Object other) {
		if (!(other instanceof VConstante)) {
			return false;
		}
		VConstante castOther = (VConstante) other;
		return Objects.equals(valeur, castOther.valeur);
	}

	@Override
	public int hashCode() {
		return Objects.hash(valeur) * 8233;
	}


}
