package decrypter.convertisseurs.condition;

import java.util.List;

import actionner.ActionMaker;
import decrypter.convertisseurs.Action;

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
