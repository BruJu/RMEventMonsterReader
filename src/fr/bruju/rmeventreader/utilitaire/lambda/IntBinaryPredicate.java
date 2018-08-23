package fr.bruju.rmeventreader.utilitaire.lambda;

/**
 * Prédicat sur deux entiers
 * 
 * @author Bruju
 *
 */
public interface IntBinaryPredicate {
	/**
	 * Teste les deux entiers
	 * @param left Premier entier
	 * @param right Second entier
	 * @return Vrai si le test est vrai
	 */
	public boolean test(int left, int right);
}
