package decrypter.toolbox;

import java.util.List;

import actionner.ReturnValue;
import decrypter.Recognizer;

public class ReturnValueIdentifier {
	private static ReturnValueIdentifier instance = null;
	private Recognizer recognizer = new Recognizer();
	
	// TODO : Faire une classe séparée pour les objets
	
	private static String pattern_DiezeNumber = "#_";		// Items
	private static String pattern_Number = "_";
	private static String pattern_Variable = "V[_]";
	private static String pattern_VariableSpace = "V[_] ";	// Items
	private static String pattern_RefVariable = "V[V[_]]";
	private static String pattern_Random = "Random [_-_]";

	public static ReturnValueIdentifier getInstance() {
		if (instance == null)
			instance = new ReturnValueIdentifier();
		
		return instance;
	}

	
	
	private ReturnValueIdentifier() {}

	public ReturnValue identify(String data) {
		List<String> argumentsLus;
		
		argumentsLus = recognizer.tryPattern(pattern_Random, data);
		if (argumentsLus != null) {
			return new ReturnValue(Integer.parseInt(argumentsLus.get(0)), Integer.parseInt(argumentsLus.get(1)));
		}
		
		argumentsLus = recognizer.tryPattern(pattern_RefVariable, data);
		if (argumentsLus != null) {
			return new ReturnValue(ReturnValue.Type.POINTER, Integer.parseInt(argumentsLus.get(0)));
		}

		argumentsLus = recognizer.tryPattern(pattern_Variable, data);
		if (argumentsLus != null) {
			return new ReturnValue(ReturnValue.Type.VARIABLE, Integer.parseInt(argumentsLus.get(0)));
		}
		
		argumentsLus = recognizer.tryPattern(pattern_VariableSpace, data);
		if (argumentsLus != null) {
			return new ReturnValue(ReturnValue.Type.VARIABLE, Integer.parseInt(argumentsLus.get(0)));
		}

		argumentsLus = recognizer.tryPattern(pattern_DiezeNumber, data);
		if (argumentsLus != null) {
			return new ReturnValue(ReturnValue.Type.VALUE, Integer.parseInt(argumentsLus.get(0)));
		}
		
		argumentsLus = recognizer.tryPattern(pattern_Number, data);
		if (argumentsLus != null) {
			return new ReturnValue(ReturnValue.Type.VALUE, Integer.parseInt(argumentsLus.get(0)));
		}
		
		return null;
	}
}
