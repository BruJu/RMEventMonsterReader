package fr.bruju.rmeventreader.actionmakers.actionner;

/**
 * Enum�ration des op�rateurs existants de comparaison et de calculs
 *
 */
public enum Operator {
	// Calculs
	/**
	 * Affectation
	 */
	AFFECTATION(null, null, (l, r) -> r),
	/**
	 * Addition
	 */
	PLUS(null, null, (l, r) -> l + r),
	/**
	 * Soustraction
	 */
	MINUS(PLUS, null, (l, r) -> l - r),
	/**
	 * Multiplication
	 */
	TIMES(null, null, (l, r) -> l * r, true),
	/**
	 * Reste de la division euclidienne
	 */
	MODULO(TIMES, null, (l, r) -> l % r, true),
	/**
	 * Division
	 */
	DIVIDE(TIMES, null, (l, r) -> l / r, true),
	// Comparaisons
	/**
	 * Identique
	 */
	IDENTIQUE(null, (l, r) -> l == r, null),
	/**
	 * Diff�rent
	 */
	DIFFERENT(IDENTIQUE, (l, r) -> l != r, null),
	/**
	 * Inf�rieur strict
	 */
	INF(null, (l, r) -> l < r, null),
	/**
	 * Sup�rieur strict
	 */
	SUP(null, (l, r) -> l > r, null),
	/**
	 * Inf�rieur ou �gal
	 */
	INFEGAL(SUP, (l, r) -> l <= r, null),
	/**
	 * Sup�rieur ou �gal
	 */
	SUPEGAL(INF, (l, r) -> l >= r, null);

	/* ============================
	 * FONCTIONEMENT D'UN OPERATEUR
	 * ============================	*/

	/** Oppos� */
	private Operator oppose;

	/** Fonction de test */
	private TestFunc testFunction;

	/** Fonction de calcul */
	private CompFunc compFunc;

	/** Vrai si le fait de mettre une op�rande � 0 rend le r�sultat �gal � 0 */
	private boolean zeroEstAbsorbant = false;

	/**
	 * Construit un op�rateur
	 * 
	 * @param oppose Op�rateur oppos� � cet op�rateur
	 * @param tstFunc Fonction permettant de tester l'op�rateur sur deux op�randes
	 * @param compFunc Fonction permettant de calculer le r�sultat en fonction de deux op�randes
	 */
	Operator(Operator oppose, TestFunc tstFunc, CompFunc compFunc) {
		this.oppose = oppose;
		if (oppose != null) {
			oppose.oppose = this;
		}
		this.testFunction = tstFunc;
		this.compFunc = compFunc;
	}

	/**
	 * Construit un op�rateur
	 * 
	 * @param oppose Op�rateur oppos� � cet op�rateur
	 * @param tstFunc Fonction permettant de tester l'op�rateur sur deux op�randes
	 * @param compFunc Fonction permettant de calculer le r�sultat en fonction de deux op�randes
	 * @param zeroEstAbsorbant Vrai si l'op�rateur a pour �l�ment absorbant z�ro
	 */
	Operator(Operator oppose, TestFunc tstFunc, CompFunc compFunc, boolean zeroEstAbsorbant) {
		this.oppose = oppose;
		if (oppose != null) {
			oppose.oppose = this;
		}
		this.testFunction = tstFunc;
		this.compFunc = compFunc;
		this.zeroEstAbsorbant = zeroEstAbsorbant;
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
	 * Fait le calcul entre les deux valeurs donn�es en utilisant l'op�rateur courant
	 * 
	 * Pour l'affectation, renvoie la valeur de droite
	 * 
	 * @param leftValue La valeur de gauche
	 * @param rightValue La valeur de droite
	 * @return Le r�sultat
	 */
	public int compute(int leftValue, int rightValue) {
		if (compFunc == null) {
			throw new OperatorErrorException("Try to compute with a compare operator");
		} else {
			return compFunc.compute(leftValue, rightValue);
		}
	}

	/**
	 * Donne le signe oppos� � l'op�rateur actuel
	 * 
	 * Impossible pour l'affectation et le modulo
	 * 
	 * @return Le signe oppos� � l'op�rateur
	 */
	public Operator revert() {
		if (oppose == null) {
			throw new UnsupportedOperationException("Can't revert " + this);
		} else {
			return oppose;
		}
	}

	/**
	 * Teste l'appartenance de cet op�rateur � la liste donn�e
	 * 
	 * @param table La liste des op�rateurs
	 * @return Vrai si l'op�rateur est dans la liste donn�e
	 */
	public boolean appartient(Operator[] table) {
		for (Operator operator : table) {
			if (operator == this)
				return true;
		}

		return false;
	}

	/**
	 * Cette fonction teste si l'application de cet op�rateur � 0 peut modifier sa valeur.
	 * 
	 * @return Vrai si l'op�rateur est la multiplication, la division ou le modulo
	 */
	public boolean isAMultiplier() {
		return zeroEstAbsorbant;
	}

	/**
	 * Exceptions concernant les op�rateurs
	 */
	public static class OperatorErrorException extends RuntimeException {
		private static final long serialVersionUID = 1178358377586546702L;

		/**
		 * Cr�e une exception sur les op�rateurs avec le message donn�
		 * 
		 * @param message Le message d�crivant l'erreur
		 */
		public OperatorErrorException(String message) {
			super("OperatorErrorException : " + message);
		}
	}

	/* ================
	 * Lambda fonctions
	 * ================ */

	private interface TestFunc {
		boolean test(int leftValue, int rightValue);
	}

	private interface CompFunc {
		int compute(int leftValue, int rightValue);
	}
}
