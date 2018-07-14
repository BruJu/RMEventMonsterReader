package monsterlist.manipulation;

import actionner.Operator;
import monsterlist.metier.Combat;

public class ConditionOnBattleId implements Condition<Combat> {
	private Operator operator;
	private int rightValue;
	
	public ConditionOnBattleId(Operator operator, int rightValue) {
		this.operator = operator;
		this.rightValue = rightValue;
	}

	@Override
	public void revert() {
		operator = operator.revert();
	}

	@Override
	public boolean filter(Combat combat) {
		return operator.test(combat.getId(), rightValue);
	}
}
