package fr.bruju.rmeventreader.implementation.printer;

import fr.bruju.rmeventreader.actionmakers.actionner.ActionMaker;
import fr.bruju.rmeventreader.actionmakers.actionner.Operator;
import fr.bruju.rmeventreader.actionmakers.donnees.Pointeur;
import fr.bruju.rmeventreader.actionmakers.donnees.ValeurAleatoire;
import fr.bruju.rmeventreader.actionmakers.donnees.ValeurFixe;
import fr.bruju.rmeventreader.actionmakers.donnees.Variable;

/**
 * Imprime les instructions qui sont reconnues
 */
public class Printer implements ActionMaker {

	/**
	 * Permet d'avoir une représentation symbolique d'un opérateur
	 * 
	 * @param operator L'opérateur à représenter
	 * @return Le signe mathématique / informatique usuellement utilisé pour représenter l'opération sous forme de
	 *         chaîne
	 */
	private String getRepresentation(Operator operator) {
		switch (operator) {
		case AFFECTATION:
			return "=";
		case DIVIDE:
			return "/=";
		case MINUS:
			return "-=";
		case MODULO:
			return "%=";
		case PLUS:
			return "+=";
		case TIMES:
			return "*=";
		case DIFFERENT:
			return "!=";
		case IDENTIQUE:
			return "==";
		case INF:
			return "<";
		case INFEGAL:
			return "<=";
		case SUP:
			return ">";
		case SUPEGAL:
			return ">=";
		}

		return null;
	}

	/**
	 * Permet d'obtenir une représentation de la valeur d'affectation d'une instruction
	 * 
	 * @param value La valeur affectée
	 * @return Une représentation sous forpme de chaîne de la valeur à affecter
	 */
	private String getRepresentation(ValeurFixe value) {
		return Integer.toString(value.get());

	}

	/**
	 * Donne la représentation d'une valeur aléatoire
	 */
	private String getRepresentation(ValeurAleatoire value) {
		return Integer.toString(value.getMin()) + "~" + Integer.toString(value.getMax());
	}

	/**
	 * Donne la représentation d'une variable
	 */
	private String getRepresentation(Variable value) {
		return "V[" + value.get() + "]";
	}

	/** Donne la représentation d'un pointeur */
	private String getRepresentation(Pointeur value) {
		return "V[V[" + value.pointeur + "]]";
	}

	/*
	 * ========================================= Redéfinition des méthodes de Action
	 * Maker =========================================
	 */

	@Override
	public void changeSwitch(Variable interrupteur, boolean value) {
		int number = interrupteur.get();

		System.out.println("Switch " + number + " = " + ((value) ? "ON" : "OFF"));
	}

	@Override
	public void changeSwitch(Pointeur interrupteur, boolean value) {
		int number = interrupteur.pointeur;
		System.out.println("Switch V[" + number + "] = " + ((value) ? "ON" : "OFF"));
	}

	@Override
	public void revertSwitch(Pointeur interrupteur) {
		int number = interrupteur.pointeur;
		System.out.println("Switch V[" + number + "] = REVERSE");
	}

	@Override
	public void revertSwitch(Variable interrupteur) {
		int number = interrupteur.get();
		System.out.println("Switch " + number + " = REVERSE");
	}

	@Override
	public void notImplementedFeature(String str) {
		System.out.println("// Non implem : " + str);
	}

	@Override
	public boolean condOnSwitch(int number, boolean value) {
		System.out.println("If Switch[" + number + "] = " + value);

		return true;
	}

	@Override
	public boolean condOnEquippedItem(int heroId, int itemId) {
		System.out.println("If Hero " + heroId + " has " + itemId);

		return true;
	}

	@Override
	public void condElse() {
		System.out.println("Else");
	}

	@Override
	public void condEnd() {
		System.out.println("End If");
	}

	@Override
	public boolean condOnVariable(int leftOperandValue, Operator operatorValue, ValeurFixe returnValue) {
		System.out.println("If V[" + leftOperandValue + "] " + getRepresentation(operatorValue) + " "
				+ getRepresentation(returnValue));

		return true;
	}

	@Override
	public void showPicture(int id, String pictureName) {
		System.out.println("Show " + id + " = " + pictureName);
	}

	@Override
	public boolean condTeamMember(int memberId) {
		System.out.println("If Hero #" + memberId + " is in the team");

		return true;
	}

	@Override
	public boolean condOnOwnedItem(int itemId) {
		System.out.println("If Item #" + itemId + " is owned");

		return true;
	}

	@Override
	public void label(int labelNumber) {
		System.out.println("Label " + labelNumber);
	}

	@Override
	public void jumpToLabel(int labelNumber) {
		System.out.println("Goto " + labelNumber);
	}

	@Override
	public void callMapEvent(int eventNumber, int eventPage) {
		System.out.println("Call Map Event " + eventNumber + " Page " + eventPage);
	}

	@Override
	public void changeVariable(Variable variable, Operator operator, ValeurFixe returnValue) {
		System.out.println(
				getRepresentation(variable) + " " + getRepresentation(operator) + " " + getRepresentation(returnValue));
	}

	@Override
	public void changeVariable(Variable variable, Operator operator, ValeurAleatoire returnValue) {
		System.out.println(
				getRepresentation(variable) + " " + getRepresentation(operator) + " " + getRepresentation(returnValue));
	}

	@Override
	public void changeVariable(Variable variable, Operator operator, Variable returnValue) {
		System.out.println(
				getRepresentation(variable) + " " + getRepresentation(operator) + " " + getRepresentation(returnValue));
	}

	@Override
	public void changeVariable(Variable variable, Operator operator, Pointeur returnValue) {
		System.out.println(
				getRepresentation(variable) + " " + getRepresentation(operator) + " " + getRepresentation(returnValue));
	}

	@Override
	public void changeVariable(Pointeur pointeur, Operator operator, ValeurFixe returnValue) {
		System.out.println(
				getRepresentation(pointeur) + " " + getRepresentation(operator) + " " + getRepresentation(returnValue));
	}

	@Override
	public void changeVariable(Pointeur pointeur, Operator operator, ValeurAleatoire returnValue) {
		System.out.println(
				getRepresentation(pointeur) + " " + getRepresentation(operator) + " " + getRepresentation(returnValue));
	}

	@Override
	public void changeVariable(Pointeur pointeur, Operator operator, Variable returnValue) {
		System.out.println(
				getRepresentation(pointeur) + " " + getRepresentation(operator) + " " + getRepresentation(returnValue));
	}

	@Override
	public void changeVariable(Pointeur pointeur, Operator operator, Pointeur returnValue) {
		System.out.println(
				getRepresentation(pointeur) + " " + getRepresentation(operator) + " " + getRepresentation(returnValue));

	}

	@Override
	public void modifyItems(ValeurFixe idItem, boolean add, ValeurFixe quantity) {
		String operator = add ? "Got " : "Lose ";

		System.out.println(operator + getRepresentation(quantity) + " of " + getRepresentation(idItem));
	}

	@Override
	public void modifyItems(ValeurFixe idItem, boolean add, Variable quantity) {
		String operator = add ? "Got " : "Lose ";

		System.out.println(operator + getRepresentation(quantity) + " of " + getRepresentation(idItem));
	}

	@Override
	public void modifyItems(Variable idItem, boolean add, ValeurFixe quantity) {
		String operator = add ? "Got " : "Lose ";

		System.out.println(operator + getRepresentation(quantity) + " of " + getRepresentation(idItem));
	}

	@Override
	public void modifyItems(Variable idItem, boolean add, Variable quantity) {
		String operator = add ? "Got " : "Lose ";

		System.out.println(operator + getRepresentation(quantity) + " of " + getRepresentation(idItem));
	}

	@Override
	public boolean condOnVariable(int leftOperandValue, Operator operatorValue, Variable returnValue) {
		System.out.println("If V[" + leftOperandValue + "] " + getRepresentation(operatorValue) + " "
				+ getRepresentation(returnValue));

		return true;
	}

	@Override
	public boolean condOnOwnedSpell(int heroId, int spellId) {
		System.out.println("If Hero " + heroId + " has Spell " + spellId);
		return true;
	}

	@Override
	public void getComment(String str) {
		System.out.println("// " + str);
	}

	@Override
	public void callCommonEvent(int eventNumber) {
		System.out.println("Call Common Event #" + eventNumber);
	}

}
