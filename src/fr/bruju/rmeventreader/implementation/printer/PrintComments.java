package fr.bruju.rmeventreader.implementation.printer;

import fr.bruju.rmeventreader.actionmakers.actionner.ActionMaker;
import fr.bruju.rmeventreader.actionmakers.actionner.Operator;
import fr.bruju.rmeventreader.actionmakers.donnees.Pointeur;
import fr.bruju.rmeventreader.actionmakers.donnees.ValeurAleatoire;
import fr.bruju.rmeventreader.actionmakers.donnees.ValeurFixe;
import fr.bruju.rmeventreader.actionmakers.donnees.Variable;

public class PrintComments implements ActionMaker {

	@Override
	public void notImplementedFeature(String str) {
		System.out.println(str);
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
		return false;
	}

	@Override
	public boolean condOnEquippedItem(int heroId, int itemId) {
		return true;
	}

	@Override
	public boolean condOnVariable(int leftOperandValue, Operator operatorValue, Variable returnValue) {
		return true;
	}

	@Override
	public boolean condOnVariable(int leftOperandValue, Operator operatorValue, ValeurFixe returnValue) {
		return true;
	}

	@Override
	public boolean condOnOwnedItem(int itemId) {
		return true;
	}

	@Override
	public boolean condTeamMember(int memberId) {
		return true;
	}

	@Override
	public boolean condOnOwnedSpell(int heroId, int spellId) {
		return true;
	}

	@Override
	public void condElse() {
	}

	@Override
	public void condEnd() {
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
