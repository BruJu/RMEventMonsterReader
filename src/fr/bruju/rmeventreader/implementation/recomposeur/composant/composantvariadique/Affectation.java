package fr.bruju.rmeventreader.implementation.recomposeur.composant.composantvariadique;

import fr.bruju.rmeventreader.implementation.recomposeur.composant.CaseMemoire;
import fr.bruju.rmeventreader.implementation.recomposeur.composant.Variadique;
import fr.bruju.rmeventreader.implementation.recomposeur.composant.bouton.Bouton;
import fr.bruju.rmeventreader.implementation.recomposeur.composant.valeur.Valeur;
import fr.bruju.rmeventreader.implementation.recomposeur.composant.visiteur.Visiteur;

import java.util.List;
import java.util.Objects;

/**
 * (Re-)Initialise la valeur d'une valeur varidique.
 * 
 * @author Bruju
 *
 * @param <T> Le type de la case mémoire
 */
public abstract class Affectation<T extends CaseMemoire> implements ComposantVariadique<Variadique<T>> {
	public static class ABouton
			extends Affectation<Bouton> {
		public ABouton(Bouton base) {
			super(base);
		}

	}

	public static class AValeur
			extends Affectation<Valeur> {
		public AValeur(Valeur base) {
			super(base);
		}
	}

	/* =========
	 * COMPOSANT
	 * ========= */
	/* Valeur affectée */
	public final T base;

	/**
	 * Affecte cette valeur variadique avec base
	 * 
	 * @param base La valeur de base
	 */
	public Affectation(T base) {
		this.base = base;
	}
	
	/* ================
	 * IMPLEMENTATIONS
	 * ================ */

	@Override
	public String toString() {
		return "|-> " + base.toString();
	}

	@Override
	public boolean cumuler(
			List<ComposantVariadique<? extends Variadique<T>>> nouveauxComposants) {
		boolean wasEmpty = nouveauxComposants.isEmpty();
		
		nouveauxComposants.clear();
		nouveauxComposants.add(this);
		
		return !wasEmpty;
	}

	/* ========
	 * VISITEUR
	 * ======== */

	@Override
	public void accept(Visiteur visiteur) {
		visiteur.visit(this);
	}

	@Override
	public Affectation<T> simplifier() {
		return this;
	}

	/* =================
	 * EQUALS / HASHCODE
	 * ================= */

	@Override
	public int hashCode() {
		return Objects.hash("AFF", base);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public boolean equals(Object object) {
		if (object instanceof Affectation) {
			Affectation that = (Affectation) object;
			return Objects.equals(this.base, that.base);
		}
		return false;
	}
}
