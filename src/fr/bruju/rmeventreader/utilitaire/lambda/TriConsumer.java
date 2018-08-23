package fr.bruju.rmeventreader.utilitaire.lambda;

/**
 * Effectue un traîtement en prenant trois arguments
 * @author Bruju
 *
 * @param <A> Type du premier argument
 * @param <B> Type du second argument
 * @param <C> Type du troisième argument
 */
public interface TriConsumer<A, B, C> {
	/**
	 * Applique la procédure sur les trois arguments
	 * @param a Premier argument
	 * @param b Second argument
	 * @param c Troisième argument
	 */
	public void consume(A a, B b, C c);
}
