package monsterlist;

import actionner.ActionMaker;
import actionner.Operator;
import actionner.ReturnValue;
import actionner.SwitchChange;
import actionner.SwitchNumber;

public class MonsterDatabaseMaker implements ActionMaker {
	private MonsterDatabase database;
	
	public MonsterDatabaseMaker() {
		database = new MonsterDatabase();
	}
	
	
	
	@Override
	public void notImplementedFeature(String str) {
	}

	@Override
	public void changeSwitch(SwitchNumber interrupteur, SwitchChange value) {
		// TODO Auto-generated method stub

	}

	@Override
	public void changeVariable(SwitchNumber variable, Operator operator, ReturnValue returnValue) {
		// TODO Auto-generated method stub

	}

	@Override
	public void changeItem(int number, int value) {
		// TODO Auto-generated method stub

	}

	@Override
	public void modifyItems(ReturnValue idItem, boolean add, ReturnValue quantity) {
		// TODO Auto-generated method stub

	}

	@Override
	public void condOnSwitch(int number, boolean value) {
		// TODO Auto-generated method stub

	}

	@Override
	public void condOnEquippedItem(int heroId, int itemId) {
		// TODO Auto-generated method stub

	}

	@Override
	public void condOnVariable(int leftOperandValue, Operator operatorValue, ReturnValue returnValue) {
		// TODO Auto-generated method stub

	}

	@Override
	public void condOnOwnedItem(int itemId) {
		// TODO Auto-generated method stub

	}

	@Override
	public void condTeamMember(int memberId) {
		// TODO Auto-generated method stub

	}

	@Override
	public void condElse() {
		// TODO Auto-generated method stub

	}

	@Override
	public void condEnd() {
		// TODO Auto-generated method stub

	}

	@Override
	public void showPicture(int id, String pictureName) {
		// TODO Auto-generated method stub

	}

	@Override
	public void label(int labelNumber) {
		// TODO Auto-generated method stub

	}

	@Override
	public void jumpToLabel(int labelNumber) {
		// TODO Auto-generated method stub

	}

}
