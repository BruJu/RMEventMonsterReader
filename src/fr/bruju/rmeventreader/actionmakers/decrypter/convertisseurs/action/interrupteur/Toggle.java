package fr.bruju.rmeventreader.actionmakers.decrypter.convertisseurs.action.interrupteur;

import java.util.List;

import fr.bruju.rmeventreader.actionmakers.actionner.ActionMaker;
import fr.bruju.rmeventreader.actionmakers.decrypter.convertisseurs.Action;
import fr.bruju.rmeventreader.actionmakers.donnees.SwitchNumber;

public class Toggle implements Action {
	private final String PATTERN = "<> Change Switch: [_-_], Toggle";
	
	@Override
	public String getPattern() {
		return PATTERN;
	}

	@Override
	public void faire(ActionMaker actionMaker, List<String> arguments) {
		int switchValueDebut = Integer.parseInt(arguments.get(0));
		int switchValueFin = Integer.parseInt(arguments.get(1));
		
		for (int i = switchValueDebut ; i <= switchValueFin ; i++) {
			actionMaker.revertSwitch(new SwitchNumber(i, false));
			
		}
	}
}
