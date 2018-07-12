package decrypter.convertisseurs.condition;

import java.util.List;

import actionner.ActionMaker;
import decrypter.convertisseurs.Action;

public class ConditionOnOwnedItem implements Action {
	private final String PATTERN = "<> Fork Condition: If Item #_ owning then ...";
	
	@Override
	public String getPattern() {
		return PATTERN;
	}

	@Override
	public void faire(ActionMaker actionMaker, List<String> arguments) {
		int itemId = Integer.parseInt(arguments.get(0));
		
		actionMaker.condOnOwnedItem(itemId);
	}

}
