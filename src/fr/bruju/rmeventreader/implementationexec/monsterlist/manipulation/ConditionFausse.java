package fr.bruju.rmeventreader.implementationexec.monsterlist.manipulation;

public class ConditionFausse<T> implements Condition<T> {
	@Override
	public void revert() {
	}

	@Override
	public boolean filter(T element) {
		return false;
	}
}
