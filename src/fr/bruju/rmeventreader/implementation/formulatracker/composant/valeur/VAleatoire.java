package fr.bruju.rmeventreader.implementation.formulatracker.composant.valeur;

import java.util.Objects;

import fr.bruju.rmeventreader.implementation.formulatracker.composant.ComposantFeuille;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.visiteur.VisiteurDeComposants;

/**
 * Valeur aléatoire, entre min et max compris avec un pas de 1.
 * 
 * @author Bruju
 *
 */
public class VAleatoire implements Valeur, ComposantFeuille {	
	/* =========
	 * COMPOSANT
	 * ========= */
	
	/** Valeur minimale */
	public final int min;
	/** Valeur maximale */
	public final int max;

	/**
	 * Crée une valeur aléatoire entre deux bornes.
	 * 
	 * @param min La valeur minimale
	 * @param max La valeur maximale
	 */
	public VAleatoire(int min, int max) {
		this.min = min;
		this.max = max;
	}

	/* ================
	 * AFFICHAGE SIMPLE
	 * ================ */

	@Override
	public String getString() {
		return min + "~" + max;
	}

	/* ========
	 * VISITEUR
	 * ======== */

	@Override
	public void accept(VisiteurDeComposants visiteurDeComposant) {
		visiteurDeComposant.visit(this);
	}

	/* =================
	 * EQUALS / HASHCODE
	 * ================= */

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
