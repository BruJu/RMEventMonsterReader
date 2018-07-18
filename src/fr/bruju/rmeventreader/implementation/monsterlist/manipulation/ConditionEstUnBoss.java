package fr.bruju.rmeventreader.implementation.monsterlist.manipulation;

import fr.bruju.rmeventreader.implementation.monsterlist.metier.Combat;

public class ConditionEstUnBoss implements Condition<Combat> {
	boolean v;
	
	public ConditionEstUnBoss(boolean value) {
		this.v = value;
	}
	
	@Override
	public void revert() {
		v = !v;
	}

	@Override
	public boolean filter(Combat element) {
		return element.isBossBattle() == v;
	}
}
