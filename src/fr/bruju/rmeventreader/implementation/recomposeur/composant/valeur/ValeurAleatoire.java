package fr.bruju.rmeventreader.implementation.recomposeur.composant.valeur;

import fr.bruju.rmeventreader.implementation.recomposeur.composant.visiteur.Visiteur;
import java.util.Objects;

/**
 * Valeur aléatoire, entre min et max compris avec un pas de 1.
 * 
 * @author Bruju
 *
 */
public class ValeurAleatoire implements Valeur {
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
	public ValeurAleatoire(int min, int max) {
		this.min = min;
		this.max = max;
	}

	/* ================
	 * AFFICHAGE SIMPLE
	 * ================ */

	@Override
	public String toString() {
		return min + "~" + max;
	}

	/* ========
	 * VISITEUR
	 * ======== */
	
	@Override
	public void accept(Visiteur visiteur) {
		visiteur.visit(this);
	}

	@Override
	public ValeurAleatoire simplifier() {
		return this;
	}
	
	/* =================
	 * EQUALS / HASHCODE
	 * ================= */

	@Override
	public int hashCode() {
		return Objects.hash(min, max);
	}

	@Override
	public boolean equals(Object object) {
		if (object instanceof ValeurAleatoire) {
			ValeurAleatoire that = (ValeurAleatoire) object;
			return this.min == that.min && this.max == that.max;
		}
		return false;
	}
}
