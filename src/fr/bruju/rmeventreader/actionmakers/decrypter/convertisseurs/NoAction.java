package fr.bruju.rmeventreader.actionmakers.decrypter.convertisseurs;

import java.util.List;

import fr.bruju.rmeventreader.actionmakers.actionner.ActionMaker;

/**
 * Fonctionnalité non implémentée
 */
public class NoAction implements Action {
	private final String str;
	private final String pattern;
	
	public NoAction (String str, String pattern) {
		this.str = str;
		this.pattern = pattern;
	}

	@Override
	public void faire(ActionMaker actionMaker, List<String> arguments) {
		actionMaker.notImplementedFeature(str);
	}

	@Override
	public String getPattern() {
		return pattern;
	}

}
