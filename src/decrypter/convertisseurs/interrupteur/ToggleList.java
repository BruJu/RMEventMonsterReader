package decrypter.convertisseurs.interrupteur;

import java.util.List;

import actionner.ActionMaker;
import actionner.SwitchChange;
import actionner.SwitchNumber;
import decrypter.convertisseurs.Action;

public class ToggleList implements Action {
	private final String PATTERN = "<> Change Switch: [_], Toggle";
	
	@Override
	public String getPattern() {
		return PATTERN;
	}

	@Override
	public void faire(ActionMaker actionMaker, List<String> arguments) {
		int switchValue = Integer.parseInt(arguments.get(0));
		
		actionMaker.changeSwitch(new SwitchNumber(switchValue, false), SwitchChange.REVERSE);
	}
}
