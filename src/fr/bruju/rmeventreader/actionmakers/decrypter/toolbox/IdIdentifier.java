package fr.bruju.rmeventreader.actionmakers.decrypter.toolbox;

import java.util.List;

import fr.bruju.rmeventreader.actionmakers.donnees.Pointeur;
import fr.bruju.rmeventreader.actionmakers.donnees.Variable;
import fr.bruju.rmeventreader.actionmakers.donnees.VariablePlage;
import fr.bruju.rmeventreader.filereader.Recognizer;
import fr.bruju.rmeventreader.actionmakers.donnees.LeftValue;

public class IdIdentifier {
	private static IdIdentifier instance = null;
	
	private final String leftValuePattern_Simple = "[_]";
	private final String leftValuePattern_Multiple = "[_-_]";
	private final String leftValuePattern_Variable = "[V[_]]";
	
	
	public static IdIdentifier getInstance() {
		if (instance == null)
			instance = new IdIdentifier();
		
		return instance;
	}
	
	
	public LeftValue identify(String data) {
		// POINTED VARIABLE
		List<String> value = Recognizer.tryPattern(leftValuePattern_Variable, data);
		if (value != null) {
			int id = Integer.parseInt(value.get(0));
			
			return new Pointeur(id);
		}
		
		// MULTIPLE VARIABLES
		value = Recognizer.tryPattern(leftValuePattern_Multiple, data);
		if (value != null) {
			int id = Integer.parseInt(value.get(0));
			int id2 = Integer.parseInt(value.get(0));
			
			return new VariablePlage(id, id2);
		}
		
		// SINGLE VARIABLE
		value = Recognizer.tryPattern(leftValuePattern_Simple, data);
		if (value != null) {
			int id = Integer.parseInt(value.get(0));
			
			return new Variable(id);
		}
		
		return null;
	}
	
	
	
	private IdIdentifier() {
		
	}
}
