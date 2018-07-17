package fr.bruju.rmeventreader.actionmakers.decrypter.convertisseurs;

import java.util.List;

import fr.bruju.rmeventreader.actionmakers.actionner.ActionMaker;

/**
 * Détection d'un appel à un évènement sur la carte
 * 
 * @author Bruju
 *
 */
public class MapEventAction implements Action {
	private final String PATTERN = "<> Call Event: Map Event #_, Page #_";

	@Override
	public String getPattern() {
		return PATTERN;
	}

	@Override
	public void faire(ActionMaker actionMaker, List<String> arguments) {
		int map = Integer.parseInt(arguments.get(0));
		int page = Integer.parseInt(arguments.get(1));

		actionMaker.callMapEvent(map, page);
	}

}
