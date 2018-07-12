package decrypter.convertisseurs.condition;

import java.util.List;

import actionner.ActionMaker;
import decrypter.convertisseurs.Action;

/**
 * Branche alternative d'une condition
 */
public class ElseFork implements Action {
	private final String PATTERN = ": Else ...";

	@Override
	public String getPattern() {
		return PATTERN;
	}

	@Override
	public void faire(ActionMaker actionMaker, List<String> arguments) {
		actionMaker.condElse();
	}
}
