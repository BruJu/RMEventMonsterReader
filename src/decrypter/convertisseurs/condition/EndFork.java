package decrypter.convertisseurs.condition;

import java.util.List;

import actionner.ActionMaker;
import decrypter.convertisseurs.Action;

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
