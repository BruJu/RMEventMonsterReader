package actionner;

public enum Operator {
	// Calculs
	AFFECTATION, PLUS, MINUS, TIMES, DIVIDE, MODULO,
	// Comparaisons
	IDENTIQUE, DIFFERENT, INF, SUP, INFEGAL, SUPEGAL;
	
	
	/**
	 * Test if leftValue Operator rightValue
	 * @param leftValue The first value to test
	 * @param rightValue The second value to test
	 * @return True if leftValue Operator rightValue is true
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
	 * Compute leftValue operator rightValue
	 * 
	 * For the affectation, returns rightValue
	 * @param leftValue The first value
	 * @param rightValue The second value
	 * @return leftValue operator rightValue
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
	
	
}
