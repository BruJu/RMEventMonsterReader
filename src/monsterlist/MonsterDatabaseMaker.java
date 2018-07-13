package monsterlist;

import java.util.ArrayList;
import java.util.List;

import actionner.ActionMakerWithConditionalInterest;
import actionner.Operator;
import actionner.ReturnValue;
import actionner.SwitchNumber;

public class MonsterDatabaseMaker implements ActionMakerWithConditionalInterest {
	private List<Condition> conditionsActuelles = new ArrayList<>();

	private MonsterDatabase database;
	
	
	// With Conditional Interest

	@Override
	public boolean caresAboutCondOnVariable(int leftOperandValue, Operator operatorValue, ReturnValue returnValue) {
		return leftOperandValue == MonsterDatabase.POS_ID_COMBAT;
	}
	
	public MonsterDatabaseMaker() {
		database = new MonsterDatabase();
	}
	
	public MonsterDatabase get() {
		return database;
	}
	
	@Override
	public void changeVariable(SwitchNumber variable, Operator operator, ReturnValue returnValue) {
		MonsterDatabase.setVariable(database.filter(conditionsActuelles), variable, operator, returnValue);
	}
	
	@Override
	public void condOnVariable(int leftOperandValue, Operator operatorValue, ReturnValue returnValue) {
		conditionsActuelles.add(new Condition(operatorValue, returnValue.value));
		
		if (operatorValue == Operator.IDENTIQUE) {
			this.database.addCombat(returnValue.value);
		}
	}
	
	@Override
	public void condElse() {
		conditionsActuelles.get(conditionsActuelles.size() - 1).revert();
	}

	@Override
	public void condEnd() {
		conditionsActuelles.remove(conditionsActuelles.size() - 1);
	}
	
	/*
	 * FONCTIONS NON INTERESSEES
	 */

	@Override
	public boolean caresAboutCondOnSwitch(int number, boolean value) {
		return false;
	}

	@Override
	public boolean caresAboutCondOnEquippedItem(int heroId, int itemId) {
		return false;
	}

	@Override
	public boolean caresAboutCondOnOwnedItem(int itemId) {
		return false;
	}

	@Override
	public boolean caresAboutCondTeamMember(int memberId) {
		return false;
	}
	

}
