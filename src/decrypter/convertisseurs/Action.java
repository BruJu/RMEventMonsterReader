package decrypter.convertisseurs;

import java.util.List;

import actionner.ActionMaker;

public interface Action {
	public String getPattern();
	
	public void faire(ActionMaker actionMaker, List<String> arguments);
}

