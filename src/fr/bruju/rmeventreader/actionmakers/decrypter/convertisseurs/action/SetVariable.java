package fr.bruju.rmeventreader.actionmakers.decrypter.convertisseurs.action;

import java.util.List;

import fr.bruju.rmeventreader.actionmakers.actionner.ActionMaker;
import fr.bruju.rmeventreader.actionmakers.actionner.Operator;
import fr.bruju.rmeventreader.actionmakers.decrypter.convertisseurs.Action;
import fr.bruju.rmeventreader.actionmakers.decrypter.toolbox.IdIdentifier;
import fr.bruju.rmeventreader.actionmakers.decrypter.toolbox.OperatorIdentifier;
import fr.bruju.rmeventreader.actionmakers.decrypter.toolbox.ReturnValueIdentifier;
import fr.bruju.rmeventreader.actionmakers.donnees.Pointeur;
import fr.bruju.rmeventreader.actionmakers.donnees.RightValue;
import fr.bruju.rmeventreader.actionmakers.donnees.ValeurAleatoire;
import fr.bruju.rmeventreader.actionmakers.donnees.ValeurFixe;
import fr.bruju.rmeventreader.actionmakers.donnees.Variable;
import fr.bruju.rmeventreader.actionmakers.donnees.VariablePlage;
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


	/**
	 * Exception jetée lorsqu'un type de valeur n'a pas été prévu
	 */
	private static class UnkownVariableTypeException extends RuntimeException {
		/**
		 * UID
		 */
		private static final long serialVersionUID = -8489136065148619931L;
		
		/**
		 * Construit l'exception
		 */
		public UnkownVariableTypeException() {
			super("UnkownVariableTypeException");
		}
	}

}
