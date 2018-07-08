package decrypter;

import java.util.List;

import actionner.ActionMaker;
import actionner.SwitchChange;

public class Decrypter {
	private ActionMaker actionMaker;
	private Recognizer recognizer;

	public Decrypter(ActionMaker actionMaker) {
		this.actionMaker = actionMaker;
		this.recognizer = new Recognizer();
	}

	public void decript(String line) {
		ElementDecrypte elementDecrypte = recognizer.recognize(line);
		
		doCorrespondingAction(elementDecrypte.instruction, elementDecrypte.arguments);
	}

	private void doCorrespondingAction(Instruction instruction, List<String> arguments) {
		switch (instruction) {
		case CHGSWITCH:
			SwitchChange switchChange = parseSwitchChange(arguments.get(1));
			actionMaker.changeSwitch(Integer.parseInt(arguments.get(0)), switchChange);
			break;
		case CHGVAR:
			throw new UnsupportedOperationException("Not Yet implemented");
			// break;
		case CHGITEM:
			throw new UnsupportedOperationException("Not Yet implemented");
			// break;
		default:
			break;
		}
	}

	private SwitchChange parseSwitchChange(String string) {
		if (string.equals("ON")) {
			return SwitchChange.ON;
		} else if (string.equals("OFF")) {
			return SwitchChange.OFF;
		} else {
			return SwitchChange.REVERSE;
		}
	}

}
