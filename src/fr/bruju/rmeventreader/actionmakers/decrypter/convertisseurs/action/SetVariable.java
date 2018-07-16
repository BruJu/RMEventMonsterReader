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
		
		/* 
		 * Il s'agit d'un choix de design de ne pas avoir de fonctions prenant comme argument LeftValue ou RightValue
		 * afin de simplifier l'implémentation de ActionMaker (éviter de tester le type d'argument reçu en début
		 * de fonction).
		 */
		
		if (variable instanceof Variable) {
			if (rightValue instanceof ValeurFixe) {
				actionMaker.changeVariable((Variable) variable, operator, (ValeurFixe) rightValue);
			} else if (rightValue instanceof ValeurAleatoire) {
				actionMaker.changeVariable((Variable) variable, operator, (ValeurAleatoire) rightValue);
			} else if (rightValue instanceof Variable) {
				actionMaker.changeVariable((Variable) variable, operator, (Variable) rightValue);
			} else if (rightValue instanceof Pointeur) {
				actionMaker.changeVariable((Variable) variable, operator, (Pointeur) rightValue);
			} else {
				throw new UnkownVariableTypeException();
			}
		} else if (variable instanceof VariablePlage) {
			if (rightValue instanceof ValeurFixe) {
				actionMaker.changeVariable((VariablePlage) variable, operator, (ValeurFixe) rightValue);
			} else if (rightValue instanceof ValeurAleatoire) {
				actionMaker.changeVariable((VariablePlage) variable, operator, (ValeurAleatoire) rightValue);
			} else if (rightValue instanceof Variable) {
				actionMaker.changeVariable((VariablePlage) variable, operator, (Variable) rightValue);
			} else if (rightValue instanceof Pointeur) {
				actionMaker.changeVariable((VariablePlage) variable, operator, (Pointeur) rightValue);
			} else {
				throw new UnkownVariableTypeException();
			}
		} else if (variable instanceof Pointeur) {
			if (rightValue instanceof ValeurFixe) {
				actionMaker.changeVariable((Pointeur) variable, operator, (ValeurFixe) rightValue);
			} else if (rightValue instanceof ValeurAleatoire) {
				actionMaker.changeVariable((Pointeur) variable, operator, (ValeurAleatoire) rightValue);
			} else if (rightValue instanceof Variable) {
				actionMaker.changeVariable((Pointeur) variable, operator, (Variable) rightValue);
			} else if (rightValue instanceof Pointeur) {
				actionMaker.changeVariable((Pointeur) variable, operator, (Pointeur) rightValue);
			} else {
				throw new UnkownVariableTypeException();
			}
		} else {
			throw new UnkownVariableTypeException();
		}
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
