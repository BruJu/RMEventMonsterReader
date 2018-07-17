package fr.bruju.rmeventreader.actionmakers.decrypter.convertisseurs.condition;

import java.util.List;

import fr.bruju.rmeventreader.actionmakers.actionner.ActionMaker;
import fr.bruju.rmeventreader.actionmakers.decrypter.convertisseurs.Action;

/**
 * Condition sur l'équipement d'un personnage
 */
public class ConditionOnOwnedSpell implements Action {
	private final String PATTERN = "<> Fork Condition: If Hero #_ has learned skill #_ then ...";
	
	@Override
	public String getPattern() {
		return PATTERN;
	}

	@Override
	public void faire(ActionMaker actionMaker, List<String> arguments) {
		int heroId = Integer.parseInt(arguments.get(0));
		int spellId = Integer.parseInt(arguments.get(1));
		
		actionMaker.condOnOwnedSpell(heroId, spellId);
	}

}
