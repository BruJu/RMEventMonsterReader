package fr.bruju.rmeventreader.implementation.recomposeur.composant.composantvariadique;

import java.util.List;
import java.util.Objects;

import fr.bruju.rmeventreader.implementation.recomposeur.composant.Variadique;
import fr.bruju.rmeventreader.implementation.recomposeur.composant.bouton.Bouton;
import fr.bruju.rmeventreader.implementation.recomposeur.composant.bouton.BoutonConstant;
import fr.bruju.rmeventreader.implementation.recomposeur.composant.composantvariadique.Affectation.ABouton;
import fr.bruju.rmeventreader.implementation.recomposeur.composant.visiteur.Visiteur;
import fr.bruju.rmeventreader.utilitaire.Utilitaire.Pile;

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

	@Override
	public boolean cumuler(List<ComposantVariadique<? extends Variadique<Bouton>>> liste) {
		if (liste.isEmpty()) {
			liste.add(this);
			return true;
		}
		
		ComposantVariadique<? extends Variadique<Bouton>> s = Pile.sommet(liste);
		
		if (s instanceof ABouton) {
			ABouton ss = (ABouton) s;
			
			if (ss.base instanceof BoutonConstant) {
				Pile.pop(liste);
				liste.add(new ABouton(((BoutonConstant) ss.base).inverser()));
				
				return false;
			}
		} else if (s instanceof Flip) {
			Pile.pop(liste);
			return false;
		}
		
		liste.add(this);
		return true;
	}
}
