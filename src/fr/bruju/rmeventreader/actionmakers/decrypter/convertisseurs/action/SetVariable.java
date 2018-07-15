package fr.bruju.rmeventreader.actionmakers.decrypter.convertisseurs.action;

import java.util.List;

import fr.bruju.rmeventreader.actionmakers.actionner.ActionMaker;
import fr.bruju.rmeventreader.actionmakers.actionner.Operator;
import fr.bruju.rmeventreader.actionmakers.decrypter.convertisseurs.Action;
import fr.bruju.rmeventreader.actionmakers.decrypter.toolbox.IdIdentifier;
import fr.bruju.rmeventreader.actionmakers.decrypter.toolbox.OperatorIdentifier;
import fr.bruju.rmeventreader.actionmakers.decrypter.toolbox.ReturnValueIdentifier;
import fr.bruju.rmeventreader.actionmakers.donnees.ReturnValue;
import fr.bruju.rmeventreader.actionmakers.donnees.rework.LeftValue;
import fr.bruju.rmeventreader.actionmakers.donnees.rework.Pointeur;
import fr.bruju.rmeventreader.actionmakers.donnees.rework.Variable;
import fr.bruju.rmeventreader.actionmakers.donnees.rework.VariablePlage;

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
		ReturnValue returnValue = ReturnValueIdentifier.getInstance().identify(value);
		
		if (variable instanceof Variable) {
			actionMaker.changeVariable((Variable) variable, operator, returnValue);
			
			return;
		}
		

		if (variable instanceof VariablePlage) {
			actionMaker.changeVariable((VariablePlage) variable, operator, returnValue);
			
			return;
		}
		

		if (variable instanceof Pointeur) {
			actionMaker.changeVariable((Pointeur) variable, operator, returnValue);
			
			return;
		}
		
		throw new UnkownVariableTypeException();
	}


	public static class UnkownVariableTypeException extends RuntimeException {
		/**
		 * UID
		 */
		private static final long serialVersionUID = -8489136065148619931L;

		public UnkownVariableTypeException() {
			super("UnkownVariableTypeException");
		}
	}

}
