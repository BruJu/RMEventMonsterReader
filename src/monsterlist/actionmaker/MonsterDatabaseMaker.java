package monsterlist.actionmaker;

import java.util.List;

import actionner.Operator;
import actionner.ReturnValue;
import actionner.SwitchChange;
import actionner.SwitchNumber;
import monsterlist.manipulation.ConditionOnBattleId;
import monsterlist.metier.Combat;
import monsterlist.metier.MonsterDatabase;

public class MonsterDatabaseMaker extends StackedActionMaker<Combat> {
	
	@Override
	protected List<Combat> getAllElements() {
		return database.extractBattles();
	}

	private MonsterDatabase database;
	
	@Override
	public void changeSwitch(SwitchNumber interrupteur, SwitchChange value) {
		if (interrupteur.numberDebut != MonsterDatabase.POS_BOSSBATTLE
				|| interrupteur.pointed
				|| value != SwitchChange.ON) {
			return;
		}
		
		MonsterDatabase.setBossBattle(getElementsFiltres());
	}

	@Override
	public boolean caresAboutCondOnVariable(int leftOperandValue, Operator operatorValue, ReturnValue returnValue) {
		return leftOperandValue == MonsterDatabase.POS_ID_COMBAT;
	}
	
	public MonsterDatabaseMaker(MonsterDatabase monsterDatabase) {
		database = monsterDatabase;
	}
	
	public MonsterDatabase get() {
		return database;
	}
	
	@Override
	public void changeVariable(SwitchNumber variable, Operator operator, ReturnValue returnValue) {
		MonsterDatabase.setVariable(getElementsFiltres(), variable, operator, returnValue);
	}
	
	@Override
	public void condOnVariable(int leftOperandValue, Operator operatorValue, ReturnValue returnValue) {
		conditions.push(new ConditionOnBattleId(operatorValue, returnValue.value));
		
		if (operatorValue == Operator.IDENTIQUE) {
			this.database.addCombat(returnValue.value);
		}
	}
	


}
