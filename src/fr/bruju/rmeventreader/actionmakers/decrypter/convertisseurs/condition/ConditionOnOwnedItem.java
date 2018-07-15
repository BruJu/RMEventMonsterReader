package fr.bruju.rmeventreader.actionmakers.decrypter.convertisseurs.condition;

import java.util.List;

import fr.bruju.rmeventreader.actionmakers.actionner.ActionMaker;
import fr.bruju.rmeventreader.actionmakers.decrypter.convertisseurs.Action;

/**
 * Condition sur la possession d'un objet
 */
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
