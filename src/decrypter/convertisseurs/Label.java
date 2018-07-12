package decrypter.convertisseurs;

import java.util.List;

import actionner.ActionMaker;

public class Label implements Action {
	private final String PATTERN = "<> Label: _";

	@Override
	public String getPattern() {
		return PATTERN;
	}

	@Override
	public void faire(ActionMaker actionMaker, List<String> arguments) {
		actionMaker.label(Integer.parseInt(arguments.get(0)));
	}

}
