package monsterlist.actionmaker;

import java.util.ArrayList;
import java.util.List;

import actionner.ActionMakerWithConditionalInterest;
import actionner.Operator;
import actionner.ReturnValue;
import actionner.SwitchChange;
import actionner.SwitchNumber;
import monsterlist.metier.Combat;
import monsterlist.metier.MonsterDatabase;

public class MonsterDatabaseMaker implements ActionMakerWithConditionalInterest {
	
	
	private List<Condition> conditionsActuelles = new ArrayList<>();

	private MonsterDatabase database;
	
	private List<Combat> getFilteredBattles() {
		List<Combat> combats = new ArrayList<>();
		
		database.extractBattles()
				.stream()
				.filter(combat -> Condition.filter(conditionsActuelles, combat))
				.forEach(combats::add);
			
		 return combats;
	}
	
	
	@Override
	public void changeSwitch(SwitchNumber interrupteur, SwitchChange value) {
		if (interrupteur.numberDebut != MonsterDatabase.POS_BOSSBATTLE
				|| interrupteur.pointed
				|| value != SwitchChange.ON) {
			return;
		}
		
		MonsterDatabase.setBossBattle(getFilteredBattles());
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
		MonsterDatabase.setVariable(getFilteredBattles(), variable, operator, returnValue);
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
	
	
	public static class Condition {
		private boolean isActiv;
		
		private Operator operator;
		private int rightValue;
		
		public Condition() {
			isActiv = false;
		}
		
		public Condition(Operator operator, int rightValue) {
			isActiv = true;
			
			this.operator = operator;
			this.rightValue = rightValue;
		}
		
		public void revert() {
			if (!isActiv) {
				return;
			}
			
			operator = operator.revert();
		}
		
		public static boolean filter(List<Condition> conditions, Combat combat) {
			for (Condition condition : conditions) {
				if (!condition.filter(combat)) {
					return false;
				}
			}
			
			return true;
		}
		
		private boolean filter(Combat combat) {
			if (!isActiv) {
				return true;
			}
			
			return operator.test(combat.getId(), rightValue);
		}
	}

}
