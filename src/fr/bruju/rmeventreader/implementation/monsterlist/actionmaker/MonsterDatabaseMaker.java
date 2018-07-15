package fr.bruju.rmeventreader.implementation.monsterlist.actionmaker;

import java.util.List;

import fr.bruju.rmeventreader.actionmakers.actionner.Operator;
import fr.bruju.rmeventreader.actionmakers.donnees.ValeurFixe;
import fr.bruju.rmeventreader.actionmakers.donnees.Variable;
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
	public void changeSwitch(Variable interrupteur, boolean value) {
		if (interrupteur.get() != MonsterDatabase.POS_BOSSBATTLE || !value) {
			return;
		}
		
		MonsterDatabase.setBossBattle(getElementsFiltres());
	}

	
	public MonsterDatabaseMaker(MonsterDatabase monsterDatabase) {
		database = monsterDatabase;
	}
	
	public MonsterDatabase get() {
		return database;
	}
	
	@Override
	public void changeVariable(Variable variable, Operator operator, ValeurFixe returnValue) {
		MonsterDatabase.setVariable(getElementsFiltres(), variable, operator, returnValue);
	}
	
	@Override
	public boolean condOnVariable(int leftOperandValue, Operator operatorValue, ValeurFixe returnValue) {
		if (leftOperandValue != MonsterDatabase.POS_ID_COMBAT)
			return false;
		
		conditions.push(new ConditionOnBattleId(operatorValue, returnValue.get()));
		
		if (operatorValue == Operator.IDENTIQUE) {
			this.database.addCombat(returnValue.get());
		}
		
		return true;
	}


	@Override
	public boolean condOnSwitch(int number, boolean value) {
		if (number != MonsterDatabase.POS_BOSSBATTLE)
			return false;
		
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

		return true;
	}
	


}
