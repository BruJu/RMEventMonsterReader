package fr.bruju.rmeventreader.actionmakers.decrypter.convertisseurs;

import java.util.List;

import fr.bruju.rmeventreader.actionmakers.actionner.ActionMaker;

/**
 * Détection d'un commentaire
 * 
 * @author Bruju
 *
 */
public class Commentary implements Action {
	private final String PATTERN = "<> Comment: _";

	@Override
	public String getPattern() {
		return PATTERN;
	}

	@Override
	public void faire(ActionMaker actionMaker, List<String> arguments) {
		actionMaker.getComment(arguments.get(0));
	}
}
