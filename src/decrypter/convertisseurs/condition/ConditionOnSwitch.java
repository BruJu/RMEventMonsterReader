package decrypter.convertisseurs.condition;

import java.util.List;

import actionner.ActionMaker;
import decrypter.convertisseurs.Action;

public class ConditionOnSwitch implements Action {
	private final String PATTERN = "<> Fork Condition: If Switch [_] == _ then ...";
	
	@Override
	public String getPattern() {
		return PATTERN;
	}

	@Override
	public void faire(ActionMaker actionMaker, List<String> arguments) {
		int switchNumber = Integer.parseInt(arguments.get(0));
		boolean switchWantedValue = arguments.get(1).equals("ON"); 
		
		actionMaker.condOnSwitch(switchNumber, switchWantedValue);
	}
}
