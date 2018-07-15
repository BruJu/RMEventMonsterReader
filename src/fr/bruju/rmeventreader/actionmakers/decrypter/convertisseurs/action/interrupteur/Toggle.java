package fr.bruju.rmeventreader.actionmakers.decrypter.convertisseurs.action.interrupteur;

import java.util.List;

import fr.bruju.rmeventreader.actionmakers.actionner.ActionMaker;
import fr.bruju.rmeventreader.actionmakers.decrypter.convertisseurs.Action;
import fr.bruju.rmeventreader.actionmakers.donnees.rework.Variable;

public class Toggle implements Action {
	private final String PATTERN = "<> Change Switch: [_], Toggle";
	
	@Override
	public String getPattern() {
		return PATTERN;
	}

	@Override
	public void faire(ActionMaker actionMaker, List<String> arguments) {
		int switchValue = Integer.parseInt(arguments.get(0));
		
		actionMaker.revertSwitch(new Variable(switchValue));
	}
}
