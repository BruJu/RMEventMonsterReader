package fr.bruju.rmeventreader.implementation.formulatracker.actionmaker;

import fr.bruju.rmeventreader.actionmakers.actionner.ActionMaker;
import fr.bruju.rmeventreader.actionmakers.actionner.Operator;
import fr.bruju.rmeventreader.actionmakers.donnees.Pointeur;
import fr.bruju.rmeventreader.actionmakers.donnees.ValeurAleatoire;
import fr.bruju.rmeventreader.actionmakers.donnees.ValeurFixe;
import fr.bruju.rmeventreader.actionmakers.donnees.Variable;
import fr.bruju.rmeventreader.implementation.printer.Printer;

public class PrinterITE implements ActionMaker {
	Printer printer = new Printer();
	
	public int indent = 0;

	private void printIndent() {
		for (int i = 0 ; i < indent ; i++)
			System.out.print("-");
	}
	
	@Override
	public void notImplementedFeature(String str) {
	}

	@Override
	public void changeSwitch(Variable interrupteur, boolean value) {
	}

	@Override
	public void changeSwitch(Pointeur interrupteur, boolean value) {
	}

	@Override
	public void revertSwitch(Variable interrupteur) {
	}

	@Override
	public void revertSwitch(Pointeur interrupteur) {
	}

	@Override
	public void changeVariable(Variable variable, Operator operator, ValeurFixe rightValue) {
	}

	@Override
	public void changeVariable(Variable variable, Operator operator, ValeurAleatoire rightValue) {
	}

	@Override
	public void changeVariable(Variable variable, Operator operator, Variable rightValue) {
	}

	@Override
	public void changeVariable(Variable variable, Operator operator, Pointeur rightValue) {
	}

	@Override
	public void changeVariable(Pointeur pointeur, Operator operator, ValeurFixe returnValue) {
	}

	@Override
	public void changeVariable(Pointeur pointeur, Operator operator, ValeurAleatoire returnValue) {
	}

	@Override
	public void changeVariable(Pointeur pointeur, Operator operator, Variable returnValue) {
	}

	@Override
	public void changeVariable(Pointeur pointeur, Operator operator, Pointeur returnValue) {
	}

	@Override
	public void modifyItems(ValeurFixe idItem, boolean add, ValeurFixe quantity) {
	}

	@Override
	public void modifyItems(ValeurFixe idItem, boolean add, Variable quantity) {
	}

	@Override
	public void modifyItems(Variable idItem, boolean add, ValeurFixe quantity) {
	}

	@Override
	public void modifyItems(Variable idItem, boolean add, Variable quantity) {
	}

	@Override
	public boolean condOnSwitch(int number, boolean value) {
		printIndent();
		boolean b =  printer.condOnSwitch(number, value);
		indent++;
		return b;
	}

	@Override
	public boolean condOnEquippedItem(int heroId, int itemId) {
		printIndent();
		boolean b =  printer.condOnEquippedItem(heroId, itemId);
		indent++;
		return b;
	}

	@Override
	public boolean condOnVariable(int leftOperandValue, Operator operatorValue, Variable returnValue) {
		printIndent();
		boolean b =  printer.condOnVariable(leftOperandValue, operatorValue, returnValue);
		indent++;
		return b;
	}

	@Override
	public boolean condOnVariable(int leftOperandValue, Operator operatorValue, ValeurFixe returnValue) {
		printIndent();
		boolean b =  printer.condOnVariable(leftOperandValue, operatorValue, returnValue);
		indent++;
		return b;
	}

	@Override
	public boolean condOnOwnedItem(int itemId) {
		printIndent();
		boolean b =  printer.condOnOwnedItem(itemId);
		indent++;
		return b;
	}

	@Override
	public boolean condTeamMember(int memberId) {
		printIndent();
		boolean b =  printer.condTeamMember(memberId);
		indent++;
		return b;
	}

	@Override
	public boolean condOnOwnedSpell(int heroId, int spellId) {
		printIndent();
		boolean b = printer.condOnOwnedSpell(heroId, spellId);
		indent++;
		return b;
	}

	@Override
	public void condElse() {
		this.indent --;
		printIndent();
		this.indent ++;
		printer.condElse();
	}

	@Override
	public void condEnd() {
		this.indent --;
		printIndent();
		printer.condEnd();
	}

	@Override
	public void showPicture(int id, String pictureName) {
	}

	@Override
	public void label(int labelNumber) {
	}

	@Override
	public void jumpToLabel(int labelNumber) {
	}

	@Override
	public void callMapEvent(int eventNumber, int eventPage) {
	}

	@Override
	public void callCommonEvent(int eventNumber) {
	}

	@Override
	public void getComment(String str) {
	}

}
