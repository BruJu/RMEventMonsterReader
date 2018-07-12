package decrypter.convertisseurs;

import java.util.List;

import actionner.ActionMaker;
import actionner.Operator;
import actionner.ReturnValue;
import actionner.SwitchNumber;
import decrypter.toolbox.IdIdentifier;
import decrypter.toolbox.OperatorIdentifier;
import decrypter.toolbox.ReturnValueIdentifier;

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
		
		SwitchNumber variable = IdIdentifier.getInstance().identify(variableName);
		Operator operator = OperatorIdentifier.getInstance().identify(operateur);
		ReturnValue returnValue = ReturnValueIdentifier.getInstance().identify(value);
		
		actionMaker.changeVariable(variable, operator, returnValue);
	}


}
