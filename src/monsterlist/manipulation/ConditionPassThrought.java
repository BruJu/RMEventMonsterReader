package monsterlist.manipulation;

public class ConditionPassThrought<T> implements Condition<T> {
	@Override
	public void revert() { }

	@Override
	public boolean filter(T element) {
		return true;
	}
}
