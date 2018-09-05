package fr.bruju.rmeventreader.implementation.monsterlist.manipulation;

public class ConditionFausse<T> implements Condition<T> {
	@Override
	public void revert() {
	}

	@Override
	public boolean filter(T element) {
		return false;
	}
}
