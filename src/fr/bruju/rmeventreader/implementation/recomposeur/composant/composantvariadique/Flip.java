package fr.bruju.rmeventreader.implementation.recomposeur.composant.composantvariadique;

import java.util.Objects;

import fr.bruju.rmeventreader.implementation.recomposeur.composant.Variadique;
import fr.bruju.rmeventreader.implementation.recomposeur.composant.bouton.Bouton;
import fr.bruju.rmeventreader.implementation.recomposeur.composant.visiteur.Visiteur;

/**
 * Inverse l'état de l'interrupteur
 * 
 * @author Bruju
 *
 */
public class Flip implements ComposantVariadique<Variadique<Bouton>> {
	/* ================
	 * AFFICHAGE SIMPLE
	 * ================ */
	
	@Override
	public String toString() {
		return "Flip";
	}

	/* ========
	 * VISITEUR
	 * ======== */

	@Override
	public void accept(Visiteur visiteur) {
		visiteur.visit(this);
	}

	@Override
	public Flip simplifier() {
		return this;
	}

	/* =================
	 * EQUALS / HASHCODE
	 * ================= */

	@Override
	public boolean equals(final Object other) {
		return other instanceof Flip;
	}

	@Override
	public int hashCode() {
		return Objects.hash("FLIP");
	}
}
