package decrypter.convertisseurs.action.interrupteur;

import java.util.List;

import actionner.ActionMaker;
import actionner.SwitchChange;
import actionner.SwitchNumber;
import decrypter.convertisseurs.Action;

// TODO : unifier l'implémentation de SetSwitch

public class SetSwitch implements Action {
	private final String PATTERN = "<> Change Switch: [_] = _";
	
	@Override
	public String getPattern() {
		return PATTERN;
	}

	@Override
	public void faire(ActionMaker actionMaker, List<String> arguments) {
		int switchValue = Integer.parseInt(arguments.get(0));
		SwitchNumber switchNumber = new SwitchNumber(switchValue, false);
				
		String nouvelleValeur = arguments.get(1);
		
		if (nouvelleValeur.equals("ON")) {
			actionMaker.changeSwitch(switchNumber, SwitchChange.ON);
			return;
		}
		if (nouvelleValeur.equals("OFF")) {
			actionMaker.changeSwitch(switchNumber, SwitchChange.OFF);
			return;
		}
		
		actionMaker.notImplementedFeature("Erreur : setSwitch");
	}
}
