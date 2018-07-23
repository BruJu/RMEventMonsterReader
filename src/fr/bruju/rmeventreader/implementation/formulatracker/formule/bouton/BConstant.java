package fr.bruju.rmeventreader.implementation.formulatracker.formule.bouton;

import fr.bruju.rmeventreader.implementation.formulatracker.simplification.VisiteurDeComposants;
import java.util.Objects;

public class BConstant implements Bouton {
	private static Bouton[] boutons = new Bouton[] {new BConstant(true), new BConstant(false)};
	
	public final boolean value;
	
	private BConstant(boolean value) {
		this.value = value;
	}

	public static Bouton get(boolean value) {
		return (value) ? boutons[0] : boutons[1];
	}

	@Override
	public String getString() {
		return (value) ? "ON" : "OFF";
	}
	

	@Override
	public void accept(VisiteurDeComposants visiteurDeComposant) {
		visiteurDeComposant.visit(this);
	}

	@Override
	public boolean equals(final Object other) {
		// Seules deux instances uniques sont créées
		return this == other;
	}

	@Override
	public int hashCode() {
		return Objects.hash(value)*1613;
	}
	
	
}
