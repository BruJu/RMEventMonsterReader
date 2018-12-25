package fr.bruju.rmeventreader.implementation.monsterlist.manipulation;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Stack;

/**
 * Une pile de conditions
 * @author Bruju
 *
 * @param <T> Le type des éléments qui sont mis à l'épreuve par les conditions
 */
public class PileDeConditions<T> {
	/** Liste des conditions */
	private Stack<Condition<T>> pile = new Stack<>();

	/**
	 * Insère une condition dans la pile
	 * @param element La condition à empiler
	 */
	public void push(Condition<T> element) {
		pile.push(element);
	}
	
	/**
	 * Enlève de la pile la dernière condition insérée encore présente
	 */
	public void pop() {
		pile.pop();
	}
	
	/**
	 * Inverse la condition au sommet de la pile
	 */
	public void revertTop() {
		pile.peek().revert();
	}
	
	/**
	 * Filtre la collection donnée pour ne garder que les éléments respectant toutes les conditions de la pile
	 * @param listeAFiltrer La collection d'éléments à filtrer
	 * @return Une collection d'éléments contenant les éléments de la liste donnée en paramètres qui respectent toutes
	 * les conditions de la pile.
	 */
	public Collection<T> respecteToutesLesConditions(Collection<T> listeAFiltrer) {
		List<T> elementsFiltres = new ArrayList<>();

		for (T element : listeAFiltrer) {
			if (respecteToutesLesConditions(pile, element)) {
				elementsFiltres.add(element);
			}
		}

		return elementsFiltres;
	}

	/**
	 * Permet de savoir si l'élément respecte toutes les conditions données
	 * @param conditions Les conditions
	 * @param element L'élément
	 * @return Vrai si l'élément respecte toutes les conditions
	 */
	static <T> boolean respecteToutesLesConditions(Stack<Condition<T>> conditions, T element) {
		for (Condition<T> condition : conditions) {
			if (!condition.filter(element)) {
				return false;
			}
		}

		return true;
	}
}
