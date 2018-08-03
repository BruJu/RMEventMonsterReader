package fr.bruju.rmeventreader.implementation.recomposeur.composant;

import java.util.List;
import java.util.Objects;


/**
 * Représente une case mémoire dont la valeur est une suite de traîtements
 * 
 * @author Bruju
 *
 * @param <T> Le type de case mémoire
 */
public abstract class Variadique<T extends CaseMemoire> implements Element {
	/* =========
	 * COMPOSANT
	 * ========= */
	
	public abstract List<? extends Element> getComposants();
	
	/* ===============
	 * IMPLEMENTATIONS
	 * =============== */

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		getComposants().forEach(action -> sb.append("<").append(action.toString()).append(">"));
		return sb.toString();
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(getComposants());
	}

	/* ==============
	 * SIMPLIFICATION
	 * ============== */
	
	@Override
	public abstract Variadique<T> simplifier();
}
