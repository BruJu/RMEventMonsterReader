package fr.bruju.rmeventreader.implementation.monsterlist.manipulation;

/**
 * Condition sur des �l�ments de type T
 * @author Bruju
 *
 * @param <T> Type sur lequel porteront les conditions
 */
public interface Condition<T> {
	/**
	 * Inverse la condition pour que les �l�ments qui �taient 
	 */
	public void revert();
	
	/**
	 * Permet de savoir si l'�l�ment respecte la condition
	 * @param element L'�l�ment � tester
	 * @return Vrai si l'�l�ment respecte la condition
	 */
	public boolean filter(T element);
}