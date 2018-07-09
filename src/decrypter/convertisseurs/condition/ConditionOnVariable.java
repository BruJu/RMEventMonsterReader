package decrypter.convertisseurs.condition;

import java.util.List;

import actionner.ActionMaker;
import actionner.Operator;
import actionner.ReturnValue;
import decrypter.convertisseurs.Action;
import decrypter.toolbox.OperatorIdentifier;
import decrypter.toolbox.ReturnValueIdentifier;

public class ConditionOnVariable implements Action {
	private final String PATTERN = "<> Fork Condition: If Variable [_] _ _ then ...";
	
	@Override
	public String getPattern() {
		return PATTERN;
	}

	@Override
	public void faire(ActionMaker actionMaker, List<String> arguments) {
		String leftOperand = arguments.get(0);
		String operator = arguments.get(1);
		String rightOperand = arguments.get(2);
		
		int leftOperandValue = Integer.parseInt(leftOperand);
		Operator operatorValue = OperatorIdentifier.getInstance().identify(operator);
		ReturnValue returnValue = ReturnValueIdentifier.getInstance().identify(rightOperand);
		
		actionMaker.condOnVariable(leftOperandValue, operatorValue, returnValue);
	}
}

/*


- SCRIPT -
<> Fork Condition: If Variable [1] == 0 then ...
 <>
: End of fork
<> Fork Condition: If Variable [1] == V[1] then ...
 <>
: End of fork
<> Fork Condition: If Variable [1] <= V[1] then ...
 <>
: End of fork
<> Fork Condition: If Variable [1] < V[1] then ...
 <>
: End of fork
<> Fork Condition: If Variable [1] != V[1] then ...
 <>
: End of fork
<> Fork Condition: If Variable [1] >= V[1] then ...
 <>
: End of fork



*/