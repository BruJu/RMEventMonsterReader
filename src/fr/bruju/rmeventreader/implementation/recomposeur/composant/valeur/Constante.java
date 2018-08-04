package fr.bruju.rmeventreader.implementation.recomposeur.composant.valeur;

import fr.bruju.rmeventreader.implementation.recomposeur.composant.ElementFeuille;
import fr.bruju.rmeventreader.implementation.recomposeur.composant.visiteur.Visiteur;
import java.util.Objects;

/**
 * Valeur constante 
 * @author Bruju
 *
 */
public class Constante implements Valeur, ElementFeuille, PasAlgorithme {
	/* =========
	 * COMPOSANT
	 * ========= */
	
	/** Valeur contenue dans la constante */
	public final int valeur;

	/** Crée une valeur constante */
	public Constante(int valeur) {
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
	public Constante simplifier() {
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
		if (object instanceof Constante) {
			Constante that = (Constante) object;
			return this.valeur == that.valeur;
		}
		return false;
	}
}