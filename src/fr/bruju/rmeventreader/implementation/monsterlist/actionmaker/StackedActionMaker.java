package fr.bruju.rmeventreader.implementation.monsterlist.actionmaker;

import java.util.List;

import fr.bruju.rmeventreader.actionmakers.actionner.ActionMakerDefalse;
import fr.bruju.rmeventreader.implementation.monsterlist.manipulation.MetaStack;

/**
 * Cette classe est une implémentation partielle des ActionMakerWithConditionalInterest.
 * 
 * Elle pousse plus loin le concept de condition en proposant une structure pour gérer
 * les conditions dont on traite les instructions (en particulier imbriquées).
 * 
 * Les classes utilisant cette classe doivent :
 * - push une condition dans conditions à chaque fois qu'une fonction condOn(...) est appellée
 * - implémenter la fonction getAllElements qui donne tous les éléments à filtrer
 * 
 * En contrepartie, les fonctions peuvent utiliser la fonction getElementsFiltres() qui
 * renvoie tous les éléments qui satisfont les conditions actuelles.
 * 
 * 
 * @author Bruju
 *
 * @param <T> Le type sur lequel portent les conditions
 */
public abstract class StackedActionMaker<T> implements ActionMakerDefalse {
	/**
	 * Liste des conditions actuellement traitées
	 */
	protected MetaStack<T> conditions = new MetaStack<>();
	
	/**
	 * Permet d'obtenir la liste des éléments filtrés par l'enchaînement courant de conditions
	 * @return La liste des éléments qui respectent les conditions établies
	 */
	public List<T> getElementsFiltres() {
		return conditions.filter(getAllElements());
	}
	
	/**
	 * Donne tous les éléments de la base de données
	 * @return Une liste de tous les éléments non filtrés
	 */
	protected abstract List<T> getAllElements();

	@Override
	public void condElse() {
		conditions.revertTop();
	}

	@Override
	public void condEnd() {
		conditions.pop();
	}
}
