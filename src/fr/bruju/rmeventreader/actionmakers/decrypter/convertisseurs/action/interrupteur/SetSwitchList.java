package fr.bruju.rmeventreader.actionmakers.decrypter.convertisseurs.action.interrupteur;

import java.util.List;

import fr.bruju.rmeventreader.actionmakers.actionner.ActionMaker;
import fr.bruju.rmeventreader.actionmakers.decrypter.convertisseurs.Action;
import fr.bruju.rmeventreader.actionmakers.donnees.Variable;

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
		boolean switchChange = arguments.get(2).equals("ON");
		
		for (int i = switchValueDeb ; i <= switchValueEnd ; i++) {
			actionMaker.changeSwitch(new Variable(i), switchChange);
		}
	}
}
