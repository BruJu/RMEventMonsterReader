package fr.bruju.rmeventreader.actionmakers.actionner;

/**
 * Enumération des opérateurs existants de comparaison et de calculs
 *
 */
public enum Operator {
	// Calculs
	/**
	 * Affectation
	 */
	AFFECTATION,
	/**
	 * Addition
	 */
	PLUS,
	/**
	 * Soustraction
	 */
	MINUS,
	/**
	 * Multiplication
	 */
	TIMES,
	/**
	 * Division
	 */
	DIVIDE,
	/**
	 * Reste de la division euclidienne
	 */
	MODULO,
	// Comparaisons
	/**
	 * Identique
	 */
	IDENTIQUE,
	/**
	 * Différent
	 */
	DIFFERENT,
	/**
	 * Inférieur strict
	 */
	INF,
	/**
	 * Supérieur strict
	 */
	SUP,
	/**
	 * Inférieur ou égal
	 */
	INFEGAL,
	/**
	 * Supérieur ou égal
	 */
	SUPEGAL;
	
	
	/**
	 * Teste si la comparaison entre la valeur gauche et la valeur droite est vraie
	 * @param leftValue La valeur de gauche
	 * @param rightValue La valeur de droite
	 * @return Vrai si la comparaison entre les deux valeurs est vraie
	 */
	public boolean test(int leftValue, int rightValue) {
		switch (this) {
		case DIFFERENT:
			return leftValue != rightValue;
		case IDENTIQUE:
			return leftValue == rightValue;
		case INF:
			return leftValue < rightValue;
		case INFEGAL:
			return leftValue <= rightValue;
		case SUP:
			return leftValue > rightValue;
		case SUPEGAL:
			return leftValue >= rightValue;

		case MODULO:
		case MINUS:
		case AFFECTATION:
		case PLUS:
		case DIVIDE:
		case TIMES:
		default:
			throw new UnsupportedOperationException("Try to test with a calculation operator");
		}
	}
	

	/**
	 * Fait le calcul entre les deux valeurs données en utilisant l'opérateur courant
	 * 
	 * Pour l'affectation, renvoie la valeur de droite
	 * @param leftValue La valeur de gauche
	 * @param rightValue La valeur de droite
	 * @return Le résultat
	 */
	public int compute(int leftValue, int rightValue) {
		switch (this) {
		case MODULO:
			return leftValue % rightValue;
		case MINUS:
			return leftValue - rightValue;
		case AFFECTATION:
			return rightValue;
		case PLUS:
			return leftValue + rightValue;
		case DIVIDE:
			return leftValue / rightValue;
		case TIMES:
			return leftValue * rightValue;
			
		case DIFFERENT:
		case IDENTIQUE:
		case INF:
		case INFEGAL:
		case SUP:
		case SUPEGAL:
		default:
			throw new UnsupportedOperationException("Try to compute with a compare operator");
		}
	}

	/**
	 * Donne le signe opposé à l'opérateur actuel
	 * 
	 * Impossible pour l'affectation et le modulo
	 * @return Le signe opposé à l'opérateur
	 */
	public Operator revert() {
		switch(this) {
		case AFFECTATION:
			throw new OperatorErrorException("Can't revert an affectation operator");
		case IDENTIQUE:
			return DIFFERENT;
		case DIFFERENT:
			return IDENTIQUE;
		case TIMES:
			return DIVIDE;
		case DIVIDE:
			return TIMES;
		case INF:
			return SUPEGAL;
		case INFEGAL:
			return SUP;
		case MINUS:
			return PLUS;
		case MODULO:
			throw new OperatorErrorException("Can't revert a modulo operator");
		case PLUS:
			return MINUS;
		case SUP:
			return INFEGAL;
		case SUPEGAL:
			return INF;
		}
		return null;
	}
	
	/**
	 * Teste l'appartenance de cet opérateur à la liste donnée
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
	 * @return Vrai si l'opérateur est la multiplication, la division ou le modulo
	 */
	public boolean isAMultiplier() {
		final Operator[] op = new Operator[] {TIMES, DIVIDE, MODULO};
		
		return appartient(op);
	}
	
	
	/**
	 * Exceptions concernant les opérateurs
	 */
	public static class OperatorErrorException extends RuntimeException {
		private static final long serialVersionUID = 1178358377586546702L;
		
		/**
		 * Crée une exception sur les opérateurs avec le message donné
		 * @param message Le message décrivant l'erreur
		 */
		public OperatorErrorException(String message) {
			super("OperatorErrorException : " + message);
		}
	}
}
