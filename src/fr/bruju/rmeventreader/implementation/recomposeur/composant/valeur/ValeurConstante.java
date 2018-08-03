package fr.bruju.rmeventreader.implementation.recomposeur.composant.valeur;

import fr.bruju.rmeventreader.implementation.recomposeur.composant.visiteur.Visiteur;
import java.util.Objects;

/**
 * Valeur constante 
 * @author Bruju
 *
 */
public class ValeurConstante implements Valeur {
	/* =========
	 * COMPOSANT
	 * ========= */
	
	/** Valeur contenue dans la constante */
	public final int valeur;

	/** Crée une valeur constante */
	public ValeurConstante(int valeur) {
		this.valeur = valeur;
	}

	/* ================
	 * AFFICHAGE SIMPLE
	 * ================ */

	@Override
	public String toString() {
		return Integer.toString(valeur);
	}

	/* ========
	 * VISITEUR
	 * ======== */

	@Override
	public void accept(Visiteur visiteur) {
		visiteur.visit(this);
	}
	
	@Override
	public ValeurConstante simplifier() {
		return this;
	}

	/* =================
	 * EQUALS / HASHCODE
	 * ================= */
	@Override
	public int hashCode() {
		return Objects.hash("VCONST", valeur);
	}

	@Override
	public boolean equals(Object object) {
		if (object instanceof ValeurConstante) {
			ValeurConstante that = (ValeurConstante) object;
			return this.valeur == that.valeur;
		}
		return false;
	}
}
