package decrypter.convertisseurs.action;

import java.util.List;

import actionner.ActionMaker;
import actionner.ReturnValue;
import decrypter.convertisseurs.Action;
import decrypter.toolbox.ReturnValueIdentifier;

/**
 * Modification des objets possédés
 */
public class ModifyItems implements Action {
	private final String PATTERN = "<> Change Items: _ _ _ of item _";
	
	@Override
	public String getPattern() {
		return PATTERN;
	}

	@Override
	public void faire(ActionMaker actionMaker, List<String> arguments) {
		boolean add = arguments.get(0).equals("Add");
		ReturnValue quantity = ReturnValueIdentifier.getInstance().identify(arguments.get(1));
		ReturnValue idItem = ReturnValueIdentifier.getInstance().identify(arguments.get(3));
		
		actionMaker.modifyItems(idItem, add, quantity);
	}
}
