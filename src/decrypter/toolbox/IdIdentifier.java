package decrypter.toolbox;

import java.util.List;

import actionner.SwitchNumber;
import decrypter.Recognizer;

public class IdIdentifier {
	private static IdIdentifier instance = null;
	
	private final String leftValuePattern_Simple = "[_]";
	private final String leftValuePattern_Multiple = "[_-_]";
	private final String leftValuePattern_Variable = "[V[_]]";
	
	private Recognizer recognizer = new Recognizer();
	
	
	public static IdIdentifier getInstance() {
		if (instance == null)
			instance = new IdIdentifier();
		
		return instance;
	}
	
	
	public SwitchNumber identify(String data) {
		// POINTED VARIABLE
		List<String> value = recognizer.tryPattern(leftValuePattern_Variable, data);
		if (value != null) {
			int id = Integer.parseInt(value.get(0));
			
			return new SwitchNumber(id, true);
		}
		
		// MULTIPLE VARIABLES
		value = recognizer.tryPattern(leftValuePattern_Multiple, data);
		if (value != null) {
			int id = Integer.parseInt(value.get(0));
			int id2 = Integer.parseInt(value.get(0));
			
			return new SwitchNumber(id, id2);
		}
		
		// SINGLE VARIABLE
		value = recognizer.tryPattern(leftValuePattern_Simple, data);
		if (value != null) {
			int id = Integer.parseInt(value.get(0));
			
			return new SwitchNumber(id, false);
		}
		
		return null;
	}
	
	
	
	private IdIdentifier() {
		
	}
}
