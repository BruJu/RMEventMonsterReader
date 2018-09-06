package fr.bruju.rmeventreader.implementationexec.monsterlist.manipulation;

/**
 * Condition qui est toujours vraie
 * @author Bruju
 *
 * @param <T> Le type d'éléments à filtrer
 */
public class ConditionPassThrought<T> implements Condition<T> {
	@Override
	public void revert() { }

	@Override
	public boolean filter(T element) {
		return true;
	}
}
