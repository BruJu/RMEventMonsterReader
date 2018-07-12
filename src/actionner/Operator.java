package actionner;

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
	 * Calcule entre les deux valeurs avec l'opérateur courant
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
	 * Renvoie le signe inverse de l'opérateur.
	 * 
	 * Impossible pour l'affectation et le modulo
	 * @return Le signe opposé à l'opérateur
	 */
	public Operator revert() {
		switch(this) {
		case AFFECTATION:
			throw new RuntimeException("Can't revert an affectation operator");
		case IDENTIQUE:
			break;
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
			throw new RuntimeException("Can't revert a modulo operator");
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
	 * Renvoie vrai si l'opérateur appartient à la liste d'opérateur donnée
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
	 * Renvoie vrai si l'opérateur est la multiplication, la division ou le modulo
	 * @return Vrai si l'opérateur est la multiplication, la division ou le modulo
	 */
	public boolean isAMultiplier() {
		final Operator[] op = new Operator[] {TIMES, DIVIDE, MODULO};
		
		return appartient(op);
	}
	
}
