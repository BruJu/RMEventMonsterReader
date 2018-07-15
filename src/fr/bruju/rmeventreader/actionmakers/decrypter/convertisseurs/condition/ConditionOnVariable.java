package fr.bruju.rmeventreader.actionmakers.decrypter.convertisseurs.condition;

import java.util.List;

import fr.bruju.rmeventreader.actionmakers.actionner.ActionMaker;
import fr.bruju.rmeventreader.actionmakers.actionner.Operator;
import fr.bruju.rmeventreader.actionmakers.decrypter.convertisseurs.Action;
import fr.bruju.rmeventreader.actionmakers.decrypter.toolbox.OperatorIdentifier;
import fr.bruju.rmeventreader.actionmakers.decrypter.toolbox.ReturnValueIdentifier;
import fr.bruju.rmeventreader.actionmakers.donnees.RightValue;
import fr.bruju.rmeventreader.actionmakers.donnees.ValeurFixe;
import fr.bruju.rmeventreader.actionmakers.donnees.Variable;

/**
 * Condition sur une variable
 */
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
		RightValue returnValue = ReturnValueIdentifier.getInstance().identify(rightOperand);
		
		if (returnValue instanceof ValeurFixe) {
			actionMaker.condOnVariable(leftOperandValue, operatorValue, (ValeurFixe) returnValue);
			return;
		}

		if (returnValue instanceof Variable) {
			actionMaker.condOnVariable(leftOperandValue, operatorValue, (Variable) returnValue);
			return;
		}
		
		throw new RuntimeException("ConditionOnVariable : unknown right value");
		
	}
}