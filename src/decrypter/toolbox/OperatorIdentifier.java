package decrypter.toolbox;

import actionner.Operator;

public class OperatorIdentifier {
	private static OperatorIdentifier instance = null;
	

	public static OperatorIdentifier getInstance() {
		if (instance == null) {
			instance = new OperatorIdentifier();
		}
		
		return instance;
	}


	public Operator identify(String operateur) {
		switch (operateur) {
		case "=":
			return Operator.AFFECTATION;
		case "-=":
			return Operator.MINUS;
		case "/=":
			return Operator.DIVIDE;
		case "+=":
			return Operator.PLUS;
		case "*=":
			return Operator.TIMES;
		case "Mod=":
			return Operator.MODULO;
		default:
			return null;
		
		}
	}

	
	
	private OperatorIdentifier() {}
}

