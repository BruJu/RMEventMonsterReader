package monsterlist.manipulation;

import actionner.Operator;
import monsterlist.metier.Monstre;

public class ConditionOnMonsterId implements Condition<Monstre> {
	private boolean onMonstre;
	private Operator operator;
	private int value;
	
	public ConditionOnMonsterId(boolean onMonster, Operator operator, int value) {
		this.onMonstre = onMonster;
		this.operator = operator;
		this.value = value;
	}

	@Override
	public void revert() {
		operator = operator.revert();
	}
	
	@Override
	public boolean filter(Monstre monstre) {
		if (onMonstre) {
			return operator.test(monstre.getId(), value);
		} else {
			return operator.test(monstre.getBattleId(), value);
		}
	}
}
