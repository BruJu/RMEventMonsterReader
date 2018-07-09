package decrypter.convertisseurs.interrupteur;

import java.util.List;

import actionner.ActionMaker;
import actionner.SwitchChange;
import actionner.SwitchNumber;
import decrypter.convertisseurs.Action;

public class ToggleList implements Action {
	@Override
	public String getPattern() {
		return "<> Change Switch: [_], Toggle";
	}

	@Override
	public void faire(ActionMaker actionMaker, List<String> arguments) {
		int switchValue = Integer.parseInt(arguments.get(0));
		
		actionMaker.changeSwitch(new SwitchNumber(switchValue, false), SwitchChange.REVERSE);
	}
}
