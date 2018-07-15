package fr.bruju.rmeventreader.implementation.monsterlist.manipulation;

/**
 * Condition sur des éléments de type T
 * @author Bruju
 *
 * @param <T> Type sur lequel porteront les conditions
 */
public interface Condition<T> {
	/**
	 * Inverse la condition pour que les éléments qui étaient 
	 */
	public void revert();
	
	/**
	 * Permet de savoir si l'élément respecte la condition
	 * @param element L'élément à tester
	 * @return Vrai si l'élément respecte la condition
	 */
	public boolean filter(T element);
}