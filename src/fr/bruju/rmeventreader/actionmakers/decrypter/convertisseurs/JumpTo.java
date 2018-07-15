package fr.bruju.rmeventreader.actionmakers.decrypter.convertisseurs;

import java.util.List;

import fr.bruju.rmeventreader.actionmakers.actionner.ActionMaker;

/**
 * Saut vers un label
 */
public class JumpTo implements Action {
	private final String PATTERN = "<> Jump To Label: _";

	@Override
	public String getPattern() {
		return PATTERN;
	}

	@Override
	public void faire(ActionMaker actionMaker, List<String> arguments) {
		actionMaker.jumpToLabel(Integer.parseInt(arguments.get(0)));
	}

}
