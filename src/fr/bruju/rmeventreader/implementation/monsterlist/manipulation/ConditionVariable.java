package fr.bruju.rmeventreader.implementation.monsterlist.manipulation;

public class ConditionVariable<T> implements Condition<T> {
	private boolean booleen;

	public ConditionVariable(boolean booleen) {
		this.booleen = booleen;
	}

	@Override
	public void revert() {
		this.booleen = !booleen;
	}

	@Override
	public boolean filter(T element) {
		return booleen;
	}
}
