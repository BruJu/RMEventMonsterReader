package fr.bruju.rmeventreader.implementation.monsterlist.actionmaker;

import java.util.List;

import fr.bruju.rmeventreader.actionmakers.actionner.Operator;
import fr.bruju.rmeventreader.actionmakers.actionner.ReturnValue;
import fr.bruju.rmeventreader.actionmakers.actionner.SwitchChange;
import fr.bruju.rmeventreader.actionmakers.actionner.SwitchNumber;
import fr.bruju.rmeventreader.implementation.monsterlist.manipulation.Condition;
import fr.bruju.rmeventreader.implementation.monsterlist.manipulation.ConditionOnBattleId;
import fr.bruju.rmeventreader.implementation.monsterlist.metier.Combat;
import fr.bruju.rmeventreader.implementation.monsterlist.metier.MonsterDatabase;

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

	@Override
	public boolean caresAboutCondOnSwitch(int number, boolean value) {
		return number == MonsterDatabase.POS_BOSSBATTLE;
	}

	@Override
	public void condOnSwitch(int number, boolean value) {
		conditions.push(new Condition<Combat>() {
			boolean v = value;
			
			@Override
			public void revert() {
				v = !v;
			}

			@Override
			public boolean filter(Combat element) {
				return element.isBossBattle() == v;
			}
		});
		
	}
	


}
