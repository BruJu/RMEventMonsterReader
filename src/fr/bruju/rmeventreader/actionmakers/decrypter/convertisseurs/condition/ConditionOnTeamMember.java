package fr.bruju.rmeventreader.actionmakers.decrypter.convertisseurs.condition;

import java.util.List;

import fr.bruju.rmeventreader.actionmakers.actionner.ActionMaker;
import fr.bruju.rmeventreader.actionmakers.decrypter.convertisseurs.Action;
/**
 * Condition sur la présence d'un coéquipier
 */
public class ConditionOnTeamMember implements Action {
	private final String PATTERN = "<> Fork Condition: If Hero #_ is in party then ...";

	@Override
	public String getPattern() {
		return PATTERN;
	}

	@Override
	public void faire(ActionMaker actionMaker, List<String> arguments) {
		int memberId = Integer.parseInt(arguments.get(0));
		
		actionMaker.condTeamMember(memberId);
	}
}
