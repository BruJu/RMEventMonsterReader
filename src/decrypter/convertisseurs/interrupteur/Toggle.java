package decrypter.convertisseurs.interrupteur;

import java.util.List;

import actionner.ActionMaker;
import actionner.SwitchChange;
import actionner.SwitchNumber;
import decrypter.convertisseurs.Action;

public class Toggle implements Action {
	@Override
	public String getPattern() {
		return "<> Change Switch: [_-_], Toggle";
	}

	@Override
	public void faire(ActionMaker actionMaker, List<String> arguments) {
		int switchValueDebut = Integer.parseInt(arguments.get(0));
		int switchValueFin = Integer.parseInt(arguments.get(1));
		
		for (int i = switchValueDebut ; i <= switchValueFin ; i++) {
			actionMaker.changeSwitch(new SwitchNumber(i, false), SwitchChange.REVERSE);
			
		}
	}
}
