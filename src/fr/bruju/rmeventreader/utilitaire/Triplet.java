package fr.bruju.rmeventreader.utilitaire;

/**
 * Un triplet d'éléments
 * @author Bruju
 *
 * @param <A> Le type du premier élément
 * @param <B> Le type du second élément
 * @param <C> Le type du troisième élément
 */
public class Triplet<A, B, C> {
	/** Premier élément */
	public final A a;
	/** Second élément */
	public final B b;
	/** Troisième élément */
	public final C c;
	
	/**
	 * Construit un triplet de trois éléments
 	 * @param a Premier élément
	 * @param b Second élément
	 * @param c Troisième élément
	 */
	public Triplet(A a, B b, C c) {
		this.a = a;
		this.b = b;
		this.c = c;
	}
}
