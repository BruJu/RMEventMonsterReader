package fr.bruju.rmeventreader.implementation.monsterlist.manipulation;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Permet de tester une liste d'éléments par rapport à une liste de conditions 
 * @author Bruju
 *
 */
class ConditionManipulator {
	/**
	 * Permet de filtrer la liste des éléments pour ne garder que ceux qui respectent les conditions 
	 * @param conditions La liste de conditions
	 * @param elements La liste d'éléments à filtrer
	 * @return Une liste avec tous les éléments donnés qui respectent toutes les conditions
	 */
	static <T> List<T> filterList(List<? extends Condition<T>> conditions, Collection<T> elements) {
		List<T> elementsFiltres = new ArrayList<>();
		
		elements.stream()
				.filter(element -> filter(conditions, element))
				.forEach(elementsFiltres::add);

		return elementsFiltres;
	}
	
	/**
	 * Permet de savoir si l'élément respecte toutes les conditions données
	 * @param conditions Les conditions
	 * @param element L'élément
	 * @return Vrai si l'élément respecte toutes les conditions
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
