package fr.bruju.rmeventreader.implementation.formulatracker.composant.valeur;

import java.util.Objects;

import fr.bruju.rmeventreader.implementation.formulatracker.composant.visiteur.VisiteurDeComposants;

/**
 * Valeur constante 
 * @author Bruju
 *
 */
public class VConstante implements Valeur {	
	/* =========
	 * COMPOSANT
	 * ========= */
	/** Valeur contenue dans la constante */
	public final int valeur;
	
	/** Crée une valeur constante */
	public VConstante(int valeur) {
		this.valeur = valeur;
	}
	
	/* ================
	 * AFFICHAGE SIMPLE
	 * ================ */

	@Override
	public String getString() {
		return Integer.toString(valeur);
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
