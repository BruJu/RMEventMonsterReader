package fr.bruju.rmeventreader.implementation.monsterlist.actionmaker;

import java.util.Collection;

import fr.bruju.rmdechiffreur.ExecuteurInstructions;
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
public abstract class ExecuteurAFiltre<T> implements ExecuteurInstructions {
	/**
	 * Liste des conditions actuellement traitées
	 */
	protected MetaStack<T> conditions = new MetaStack<>();
	
	/**
	 * Permet d'obtenir la liste des éléments filtrés par l'enchaînement courant de conditions
	 * @return La liste des éléments qui respectent les conditions établies
	 */
	public final Collection<T> getElementsFiltres() {
		return conditions.filter(getAllElements());
	}
	
	/**
	 * Donne tous les éléments de la base de données
	 * @return Une liste de tous les éléments non filtrés
	 */
	protected abstract Collection<T> getAllElements();
	
	@Override
	public final void Flot_siFin() {
		conditions.pop();
	}
	
	@Override
	public final void Flot_siNon() {
		conditions.revertTop();
	}
}
