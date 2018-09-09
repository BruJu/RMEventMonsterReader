package fr.bruju.rmeventreader.implementation.monsterlist.manipulation;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Une pile de conditions
 * @author Bruju
 *
 * @param <T> Le type des éléments qui sont mis à l'épreuve par les conditions
 */
public class MetaStack<T> {
	/**
	 * Liste des conditions
	 */
	private List<Condition<T>> elements;
	
	/**
	 * Construit une pile de conditions vide
	 */
	public MetaStack() {
		elements = new ArrayList<>();
	}
	
	/**
	 * Insère une condition dans la pile
	 * @param element La condition à empiler
	 */
	public void push(Condition<T> element) {
		elements.add(element);
	}
	
	/**
	 * Enlève de la pile la dernière condition insérée encore présente
	 */
	public void pop() {
		elements.remove(elements.size() - 1);
	}
	
	/**
	 * Inverse la condition au sommet de la pile
	 */
	public void revertTop() {
		elements.get(elements.size() - 1).revert();
	}
	
	/**
	 * Filtre la collection donnée pour ne garder que les éléments respectant toutes les conditions de la pile
	 * @param listeAFiltrer La collection d'éléments à filtrer
	 * @return Une collection d'éléments contenant les éléments de la liste donnée en paramètres qui respectent toutes
	 * les conditions de la pile.
	 */
	public Collection<T> filter(Collection<T> listeAFiltrer) {
		return ConditionManipulator.<T>filterList(elements, listeAFiltrer);
	}	
}
