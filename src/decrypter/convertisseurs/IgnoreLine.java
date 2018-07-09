package decrypter.convertisseurs;

import java.util.List;

import actionner.ActionMaker;

public class IgnoreLine implements Action {
	private String pattern;
	
	public IgnoreLine(String pattern) {
		this.pattern = pattern;
	}
	
	@Override
	public String getPattern() {
		return pattern;
	}

	@Override
	public void faire(ActionMaker actionMaker, List<String> arguments) {

	}
}
