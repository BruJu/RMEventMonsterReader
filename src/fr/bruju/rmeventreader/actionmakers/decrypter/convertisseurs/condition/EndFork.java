package fr.bruju.rmeventreader.actionmakers.decrypter.convertisseurs.condition;

import java.util.List;

import fr.bruju.rmeventreader.actionmakers.actionner.ActionMaker;
import fr.bruju.rmeventreader.actionmakers.decrypter.convertisseurs.Action;

/**
 * Fin de condition
 */
public class EndFork implements Action {
	private final String PATTERN = ": End of fork";

	@Override
	public String getPattern() {
		return PATTERN;
	}

	@Override
	public void faire(ActionMaker actionMaker, List<String> arguments) {
		actionMaker.condEnd();
	}
}
