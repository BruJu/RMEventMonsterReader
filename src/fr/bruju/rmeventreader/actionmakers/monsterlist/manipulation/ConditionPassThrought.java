package fr.bruju.rmeventreader.actionmakers.monsterlist.manipulation;

/**
 * Condition qui est toujours vraie
 * @author Bruju
 *
 * @param <T> Le type d'�l�ments � filtrer
 */
public class ConditionPassThrought<T> implements Condition<T> {
	@Override
	public void revert() { }

	@Override
	public boolean filter(T element) {
		return true;
	}
}
