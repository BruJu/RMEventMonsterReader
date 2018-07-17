package fr.bruju.rmeventreader.actionmakers.actionner;

import java.util.function.BiFunction;
import java.util.function.BiPredicate;

/**
 * Enumération des opérateurs existants de comparaison et de calculs
 *
 */
public enum Operator {
	// Calculs
	/**
	 * Affectation
	 */
	AFFECTATION((l, r) -> r, false),
	/**
	 * Addition
	 */
	PLUS((l, r) -> l + r, false),
	/**
	 * Soustraction
	 */
	MINUS(PLUS, (l, r) -> l - r, false),
	/**
	 * Multiplication
	 */
	TIMES((l, r) -> l * r, true),
	/**
	 * Reste de la division euclidienne
	 */
	MODULO(TIMES, (l, r) -> l % r, true),
	/**
	 * Division
	 */
	DIVIDE(TIMES, (l, r) -> l / r, false),
	// Comparaisons
	/**
	 * Identique
	 */
	IDENTIQUE((l, r) -> l == r),
	/**
	 * Différent
	 */
	DIFFERENT(IDENTIQUE, (l, r) -> l != r),
	/**
	 * Inférieur strict
	 */
	INF((l, r) -> l < r),
	/**
	 * Supérieur strict
	 */
	SUP((l, r) -> l > r),
	/**
	 * Inférieur ou égal
	 */
	INFEGAL(SUP, (l, r) -> l <= r),
	/**
	 * Supérieur ou égal
	 */
	SUPEGAL(INF, (l, r) -> l >= r);

	/* ============================
	 * FONCTIONEMENT D'UN OPERATEUR
	 * ============================	*/

	/** Opposé */
	private Operator oppose = null;

	/** Fonction de test */
	private BiPredicate<Integer, Integer> testFunction = null;

	/** Fonction de calcul */
	private BiFunction<Integer, Integer, Integer> compFunc = null;

	/** Vrai si le fait de mettre une opérande à 0 rend le résultat égal à 0 */
	private boolean zeroEstAbsorbant = false;

	
	/**
	 * Construit un opérateur de test
	 * @param fonctionTest La fonction qui teste l'opérateur à partir de deux entiers
	 */
	Operator(BiPredicate<Integer, Integer> fonctionTest) {
		this.testFunction = fonctionTest;
	}
	
	/**
	 * Construit un opérateur de test ayant un opposé
	 * 
	 * @param oppose Opérateur opposé à cet opérateur
	 * @param tstFunc Fonction permettant de tester l'opérateur sur deux opérandes
	 */
	Operator(Operator oppose, BiPredicate<Integer, Integer> fonctionTest) {
		this(fonctionTest);
		
		if (oppose != null) {
			this.oppose = oppose;
			oppose.oppose = this;
		}
	}
	
	/**
	 * Construit un opérateur de calcul
	 *  
	 * @param fonctionCalcul Fonction permettant de calculer le résultat en fonction de deux opérandes
	 * @param zeroEstAbsorbant Vrai si l'opérateur a pour élément absorbant zéro
	 */
	Operator(BiFunction<Integer, Integer, Integer> fonctionCalcul, boolean zeroEstAbsorbant) {
		this.compFunc = fonctionCalcul;
		this.zeroEstAbsorbant = zeroEstAbsorbant;
	}

	/**
	 * Construit un opérateur de calcul
	 *  
	 * @param oppose Opérateur opposé à cet opérateur
	 * @param fonctionCalcul Fonction permettant de calculer le résultat en fonction de deux opérandes
	 * @param zeroEstAbsorbant Vrai si l'opérateur a pour élément absorbant zéro
	 */
	Operator(Operator oppose, BiFunction<Integer, Integer, Integer> fonctionCalcul, boolean zeroEstAbsorbant) {
		this(fonctionCalcul, zeroEstAbsorbant);
		
		if (oppose != null) {
			this.oppose = oppose;
			oppose.oppose = this;
		}
	}

	/* ========
	 * SERVICES
	 * ======== */

	/**
	 * Teste si la comparaison entre la valeur gauche et la valeur droite est vraie
	 * 
	 * @param leftValue La valeur de gauche
	 * @param rightValue La valeur de droite
	 * @return Vrai si la comparaison entre les deux valeurs est vraie
	 */
	public boolean test(int leftValue, int rightValue) {
		if (testFunction == null) {
			throw new OperatorErrorException("Try to test with a calculation operator");
		} else {
			return testFunction.test(leftValue, rightValue);
		}
	}

	/**
	 * Fait le calcul entre les deux valeurs données en utilisant l'opérateur courant
	 * 
	 * Pour l'affectation, renvoie la valeur de droite
	 * 
	 * @param leftValue La valeur de gauche
	 * @param rightValue La valeur de droite
	 * @return Le résultat
	 */
	public int compute(int leftValue, int rightValue) {
		if (compFunc == null) {
			throw new OperatorErrorException("Try to compute with a compare operator");
		} else {
			return compFunc.apply(leftValue, rightValue);
		}
	}

	/**
	 * Donne le signe opposé à l'opérateur actuel
	 * 
	 * Impossible pour l'affectation et le modulo
	 * 
	 * @return Le signe opposé à l'opérateur
	 */
	public Operator revert() {
		if (oppose == null) {
			throw new UnsupportedOperationException("Can't revert " + this);
		} else {
			return oppose;
		}
	}

	/**
	 * Teste l'appartenance de cet opérateur à la liste donnée
	 * 
	 * @param table La liste des opérateurs
	 * @return Vrai si l'opérateur est dans la liste donnée
	 */
	public boolean appartient(Operator[] table) {
		for (Operator operator : table) {
			if (operator == this)
				return true;
		}

		return false;
	}

	/**
	 * Cette fonction teste si l'application de cet opérateur à 0 peut modifier sa valeur.
	 * 
	 * @return Vrai si l'opérateur est la multiplication, la division ou le modulo
	 */
	public boolean isAMultiplier() {
		return zeroEstAbsorbant;
	}

	/**
	 * Exceptions concernant les opérateurs
	 */
	public static class OperatorErrorException extends RuntimeException {
		private static final long serialVersionUID = 1178358377586546702L;

		/**
		 * Crée une exception sur les opérateurs avec le message donné
		 * 
		 * @param message Le message décrivant l'erreur
		 */
		public OperatorErrorException(String message) {
			super("OperatorErrorException : " + message);
		}
	}
}
