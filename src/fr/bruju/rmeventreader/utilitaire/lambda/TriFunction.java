package fr.bruju.rmeventreader.utilitaire.lambda;

/**
 * Fonction prenant trois arguments
 * @author Bruju
 *
 * @param <A> Type du premier argument
 * @param <B> Type du second argument
 * @param <C> Type du troisième argument
 * @param <R> Type de retour
 */
public interface TriFunction<A, B, C, R> {
	/**
	 * Applique la fonction aux arguments
	 * @param a Premier argument
	 * @param b Second argument
	 * @param c Troisième argument
	 * @return Valeur retournée par la fonction
	 */
	public R apply(A a, B b, C c);
}
