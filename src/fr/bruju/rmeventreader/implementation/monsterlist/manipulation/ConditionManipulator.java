package fr.bruju.rmeventreader.implementation.monsterlist.manipulation;

import java.util.ArrayList;
import java.util.List;

/**
 * Permet de tester une liste d'�l�ments par rapport � une liste de conditions 
 * @author Bruju
 *
 */
class ConditionManipulator {
	/**
	 * Permet de filtrer la liste des �l�ments pour ne garder que ceux qui respectent les conditions 
	 * @param conditions La liste de conditions
	 * @param elements La liste d'�l�ments � filtrer
	 * @return Une liste avec tous les �l�ments donn�s qui respectent toutes les conditions
	 */
	static <T> List<T> filterList(List<? extends Condition<T>> conditions, List<T> elements) {
		List<T> elementsFiltres = new ArrayList<>();
		
		elements.stream()
				.filter(element -> filter(conditions, element))
				.forEach(elementsFiltres::add);

		return elementsFiltres;
	}
	
	/**
	 * Permet de savoir si l'�l�ment respecte toutes les conditions donn�es
	 * @param conditions Les conditions
	 * @param element L'�l�ment
	 * @return Vrai si l'�l�ment respecte toutes les conditions
	 */
	static <T> boolean filter(List<? extends Condition<T>> conditions, T element) {
		for (Condition<T> condition : conditions) {
			if (!condition.filter(element)) {
				return false;
			}
		}
		
		return true;
	}
}
