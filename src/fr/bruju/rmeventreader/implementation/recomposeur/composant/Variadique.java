package fr.bruju.rmeventreader.implementation.recomposeur.composant;

/**
 * Représente une case mémoire dont la valeur est une suite de traîtements
 * 
 * @author Bruju
 *
 * @param <T> Le type de case mémoire
 */
public interface Variadique<T extends CaseMemoire> extends Element {
	@Override
	public Variadique<T> simplifier();
}
