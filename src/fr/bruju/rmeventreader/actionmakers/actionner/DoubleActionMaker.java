package fr.bruju.rmeventreader.actionmakers.actionner;

import fr.bruju.rmeventreader.actionmakers.donnees.Pointeur;
import fr.bruju.rmeventreader.actionmakers.donnees.ValeurAleatoire;
import fr.bruju.rmeventreader.actionmakers.donnees.ValeurFixe;
import fr.bruju.rmeventreader.actionmakers.donnees.Variable;

public class DoubleActionMaker implements ActionMaker {
	private ActionMaker a;
	private ActionMaker b;
	
	public DoubleActionMaker(ActionMaker a, ActionMaker b) {
		this.a = a;
		this.b = b;
	}

	@Override
	public void notImplementedFeature(String str) {
		a.notImplementedFeature(str);
		b.notImplementedFeature(str);
	}

	@Override
	public void changeSwitch(Variable interrupteur, boolean value) {
		a.changeSwitch(interrupteur, value);
		b.changeSwitch(interrupteur, value);
	}

	@Override
	public void changeSwitch(Pointeur interrupteur, boolean value) {
		a.changeSwitch(interrupteur, value);
		b.changeSwitch(interrupteur, value);
	}

	@Override
	public void revertSwitch(Variable interrupteur) {
		a.revertSwitch(interrupteur);
		b.revertSwitch(interrupteur);
	}

	@Override
	public void revertSwitch(Pointeur interrupteur) {
		a.revertSwitch(interrupteur);
		b.revertSwitch(interrupteur);
	}

	@Override
	public void changeVariable(Variable variable, Operator operator, ValeurFixe rightValue) {
		a.changeVariable(variable, operator, rightValue);
		b.changeVariable(variable, operator, rightValue);
	}

	@Override
	public void changeVariable(Variable variable, Operator operator, ValeurAleatoire rightValue) {
		a.changeVariable(variable, operator, rightValue);
		b.changeVariable(variable, operator, rightValue);
	}

	@Override
	public void changeVariable(Variable variable, Operator operator, Variable rightValue) {
		a.changeVariable(variable, operator, rightValue);
		b.changeVariable(variable, operator, rightValue);
	}

	@Override
	public void changeVariable(Variable variable, Operator operator, Pointeur rightValue) {
		a.changeVariable(variable, operator, rightValue);
		b.changeVariable(variable, operator, rightValue);
	}

	@Override
	public void changeVariable(Pointeur pointeur, Operator operator, ValeurFixe returnValue) {
		a.changeVariable(pointeur, operator, returnValue);
		b.changeVariable(pointeur, operator, returnValue);
	}

	@Override
	public void changeVariable(Pointeur pointeur, Operator operator, ValeurAleatoire returnValue) {
		a.changeVariable(pointeur, operator, returnValue);
		b.changeVariable(pointeur, operator, returnValue);
	}

	@Override
	public void changeVariable(Pointeur pointeur, Operator operator, Variable returnValue) {
		a.changeVariable(pointeur, operator, returnValue);
		b.changeVariable(pointeur, operator, returnValue);
	}

	@Override
	public void changeVariable(Pointeur pointeur, Operator operator, Pointeur returnValue) {
		a.changeVariable(pointeur, operator, returnValue);
		b.changeVariable(pointeur, operator, returnValue);
	}

	@Override
	public void modifyItems(ValeurFixe idItem, boolean add, ValeurFixe quantity) {
		a.modifyItems(idItem, add, quantity);
		b.modifyItems(idItem, add, quantity);
	}

	@Override
	public void modifyItems(ValeurFixe idItem, boolean add, Variable quantity) {
		a.modifyItems(idItem, add, quantity);
		b.modifyItems(idItem, add, quantity);
	}

	@Override
	public void modifyItems(Variable idItem, boolean add, ValeurFixe quantity) {
		a.modifyItems(idItem, add, quantity);
		b.modifyItems(idItem, add, quantity);
	}

	@Override
	public void modifyItems(Variable idItem, boolean add, Variable quantity) {
		a.modifyItems(idItem, add, quantity);
		b.modifyItems(idItem, add, quantity);
	}

	@Override
	public boolean condOnSwitch(int number, boolean value) {
		a.condOnSwitch(number, value);
		return b.condOnSwitch(number, value);
	}

	@Override
	public boolean condOnEquippedItem(int heroId, int itemId) {
		a.condOnEquippedItem(heroId, itemId);
		return b.condOnEquippedItem(heroId, itemId);
	}

	@Override
	public boolean condOnVariable(int leftOperandValue, Operator operatorValue, Variable returnValue) {
		a.condOnVariable(leftOperandValue, operatorValue, returnValue);
		return b.condOnVariable(leftOperandValue, operatorValue, returnValue);
	}

	@Override
	public boolean condOnVariable(int leftOperandValue, Operator operatorValue, ValeurFixe returnValue) {
		a.condOnVariable(leftOperandValue, operatorValue, returnValue);
		return b.condOnVariable(leftOperandValue, operatorValue, returnValue);
	}

	@Override
	public boolean condOnOwnedItem(int itemId) {
		a.condOnOwnedItem(itemId);
		return b.condOnOwnedItem(itemId);
	}

	@Override
	public boolean condTeamMember(int memberId) {
		a.condTeamMember(memberId);
		return b.condTeamMember(memberId);
	}

	@Override
	public boolean condOnOwnedSpell(int heroId, int spellId) {
		a.condOnOwnedSpell(heroId, spellId);
		return b.condOnOwnedSpell(heroId, spellId);
	}

	@Override
	public void condElse() {
		a.condElse();
		b.condElse();
	}

	@Override
	public void condEnd() {
		a.condEnd();
		b.condEnd();
	}

	@Override
	public void showPicture(int id, String pictureName) {
		a.showPicture(id, pictureName);
		b.showPicture(id, pictureName);
	}

	@Override
	public void label(int labelNumber) {
		a.label(labelNumber);
		b.label(labelNumber);
	}

	@Override
	public void jumpToLabel(int labelNumber) {
		a.jumpToLabel(labelNumber);
		b.jumpToLabel(labelNumber);
	}

	@Override
	public void callMapEvent(int eventNumber, int eventPage) {
		a.callMapEvent(eventNumber, eventPage);
		b.callMapEvent(eventNumber, eventPage);
	}

	@Override
	public void callCommonEvent(int eventNumber) {
		a.callCommonEvent(eventNumber);
		b.callCommonEvent(eventNumber);
	}

	@Override
	public void getComment(String str) {
		a.getComment(str);
		b.getComment(str);
	}

}
