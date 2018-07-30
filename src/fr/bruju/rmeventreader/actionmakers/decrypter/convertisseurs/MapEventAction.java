package fr.bruju.rmeventreader.actionmakers.decrypter.convertisseurs;

import java.util.List;

import fr.bruju.rmeventreader.actionmakers.actionner.ActionMaker;

/**
 * Détection d'un appel à un évènement commun
 * 
 * @author Bruju
 *
 */
public class MapEventAction implements Action {
	private final String PATTERN = "<> Call Event: Common Event #_";

	@Override
	public String getPattern() {
		return PATTERN;
	}

	@Override
	public void faire(ActionMaker actionMaker, List<String> arguments) {
		int eventNumber = Integer.parseInt(arguments.get(0));

		actionMaker.callCommonEvent(eventNumber);
	}

}
