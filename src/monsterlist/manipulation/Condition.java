package monsterlist.manipulation;

public interface Condition<T> {
	public void revert();
	public boolean filter(T element);
}