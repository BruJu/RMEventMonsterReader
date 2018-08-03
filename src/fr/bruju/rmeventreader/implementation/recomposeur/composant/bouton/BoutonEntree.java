package fr.bruju.rmeventreader.implementation.recomposeur.composant.bouton;

import fr.bruju.rmeventreader.implementation.recomposeur.composant.visiteur.Visiteur;
import java.util.Objects;


/**
 * Interrupteur dont la valeur est inconnue
 * 
 * @author Bruju
 *
 */
public class BoutonEntree implements Bouton {
	/* =========
	 * COMPOSANT
	 * ========= */
	
	/** Numéro de l'interrupteur */
	public final int id;

	/**
	 * Construit un interrupteur avec le numéro donné 
	 * @param id Le numéro de l'interrupteur
	 */
	public BoutonEntree(int id) {
		this.id = id;
	}

	/* ===============
	 * IMPLEMENTATIONS
	 * =============== */

	@Override
	public String toString() {
		return "S[" + id +"]";
	}

	/* ========
	 * VISITEUR
	 * ======== */
	
	@Override
	public void accept(Visiteur visiteur) {
		visiteur.visit(this);
	}
	
	@Override
	public BoutonEntree simplifier() {
		return this;
	}

	/* =================
	 * EQUALS / HASHCODE
	 * ================= */

	@Override
	public int hashCode() {
		return Objects.hash("BVAR", id);
	}

	@Override
	public boolean equals(Object object) {
		if (object instanceof BoutonEntree) {
			BoutonEntree that = (BoutonEntree) object;
			return this.id == that.id;
		}
		return false;
	}
}
