package decrypter.convertisseurs.action.interrupteur;

import java.util.List;

import actionner.ActionMaker;
import actionner.SwitchChange;
import actionner.SwitchNumber;
import decrypter.convertisseurs.Action;

public class SetSwitchList implements Action {
	private final String PATTERN = "<> Change Switch: [_-_] = _";
	
	@Override
	public String getPattern() {
		return PATTERN;
	}
	
	@Override
	public void faire(ActionMaker actionMaker, List<String> arguments) {
		int switchValueDeb = Integer.parseInt(arguments.get(0));
		int switchValueEnd = Integer.parseInt(arguments.get(1));
		SwitchChange switchChange = arguments.get(2).equals("ON") ? SwitchChange.ON : SwitchChange.OFF;
		
		for (int i = switchValueDeb ; i <= switchValueEnd ; i++) {
			actionMaker.changeSwitch(new SwitchNumber(i, false), switchChange);
		}
	}
}
