package fr.bruju.rmeventreader.actionmakers.decrypter.convertisseurs.action;

import java.util.List;

import fr.bruju.rmeventreader.actionmakers.actionner.ActionMaker;
import fr.bruju.rmeventreader.actionmakers.actionner.Operator;
import fr.bruju.rmeventreader.actionmakers.decrypter.convertisseurs.Action;
import fr.bruju.rmeventreader.actionmakers.decrypter.toolbox.IdIdentifier;
import fr.bruju.rmeventreader.actionmakers.decrypter.toolbox.OperatorIdentifier;
import fr.bruju.rmeventreader.actionmakers.decrypter.toolbox.ReturnValueIdentifier;
import fr.bruju.rmeventreader.actionmakers.donnees.RightValue;
import fr.bruju.rmeventreader.actionmakers.donnees.LeftValue;

/**
 * Modification de l'état d'une variable
 */
public class SetVariable implements Action {
	private final String PATTERN = "<> Change Variable: _ _ _";
	
	@Override
	public String getPattern() {
		return PATTERN;
	}

	@Override
	public void faire(ActionMaker actionMaker, List<String> arguments) {
		String variableName = arguments.get(0);
		String operateur = arguments.get(1);
		String value = arguments.get(2);
		
		LeftValue variable = IdIdentifier.getInstance().identify(variableName);
		Operator operator = OperatorIdentifier.getInstance().identify(operateur);
		RightValue rightValue = ReturnValueIdentifier.getInstance().identify(value);
		
		actionMaker._changeVariable(variable, operator, rightValue);
	}
}
