package fr.bruju.rmeventreader.implementation.recomposeur.composant.composantvariadique;

import fr.bruju.rmeventreader.implementation.recomposeur.composant.CaseMemoire;
import fr.bruju.rmeventreader.implementation.recomposeur.composant.Variadique;
import fr.bruju.rmeventreader.implementation.recomposeur.composant.visiteur.Visiteur;
import java.util.Objects;

/**
 * (Re-)Initialise la valeur d'une valeur varidique.
 *  
 * @author Bruju
 *
 * @param <T> Le type de la case mémoire
 */
public class Affectation<T extends CaseMemoire> implements ComposantVariadique<Variadique<T>> {
	/* =========
	 * COMPOSANT
	 * ========= */
	/* Valeur affectée */
	public final T base;
	
	/**
	 * Affecte cette valeur variadique avec base
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
