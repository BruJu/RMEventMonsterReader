package fr.bruju.rmeventreader.actionmakers.decrypter.toolbox;

import java.util.List;

import fr.bruju.rmeventreader.actionmakers.decrypter.Recognizer;
import fr.bruju.rmeventreader.actionmakers.donnees.Pointeur;
import fr.bruju.rmeventreader.actionmakers.donnees.RightValue;
import fr.bruju.rmeventreader.actionmakers.donnees.ValeurAleatoire;
import fr.bruju.rmeventreader.actionmakers.donnees.ValeurFixe;
import fr.bruju.rmeventreader.actionmakers.donnees.Variable;

public class ReturnValueIdentifier {
	private static ReturnValueIdentifier instance = null;
	
	// TODO : Faire une classe séparée pour les objets
	
	private static String pattern_DiezeNumber = "#_";		// Items
	private static String pattern_Number = "_";
	private static String pattern_Variable = "V[_]";
	private static String pattern_VariableSpace = "V[_] ";	// Items
	private static String pattern_RefVariable = "V[V[_]]";
	private static String pattern_Random = "Random [µ-µ]";

	public static ReturnValueIdentifier getInstance() {
		if (instance == null)
			instance = new ReturnValueIdentifier();
		
		return instance;
	}

	
	
	private ReturnValueIdentifier() {}

	public RightValue identify(String data) {
		List<String> argumentsLus;
		
		argumentsLus = Recognizer.tryPattern(pattern_Random, data);
		if (argumentsLus != null) {			
			return new ValeurAleatoire(Integer.parseInt(argumentsLus.get(0)), Integer.parseInt(argumentsLus.get(1)));
		}
		
		argumentsLus = Recognizer.tryPattern(pattern_RefVariable, data);
		if (argumentsLus != null) {
			return new Pointeur(Integer.parseInt(argumentsLus.get(0)));
		}

		argumentsLus = Recognizer.tryPattern(pattern_Variable, data);
		if (argumentsLus != null) {
			return new Variable(Integer.parseInt(argumentsLus.get(0)));
		}
		
		argumentsLus = Recognizer.tryPattern(pattern_VariableSpace, data);
		if (argumentsLus != null) {
			return new Variable(Integer.parseInt(argumentsLus.get(0)));
		}

		argumentsLus = Recognizer.tryPattern(pattern_DiezeNumber, data);
		if (argumentsLus != null) {
			return new ValeurFixe(Integer.parseInt(argumentsLus.get(0)));
		}
		
		argumentsLus = Recognizer.tryPattern(pattern_Number, data);
		if (argumentsLus != null) {
			return new ValeurFixe(Integer.parseInt(argumentsLus.get(0)));
		}
		
		return null;
	}
}
