package fr.bruju.rmeventreader.implementation.monsterlist.actionmaker;

import java.util.List;

import fr.bruju.rmeventreader.actionmakers.actionner.ActionMakerWithConditionalInterest;
import fr.bruju.rmeventreader.implementation.monsterlist.manipulation.MetaStack;

/**
 * Cette classe est une impl�mentation partielle des ActionMakerWithConditionalInterest.
 * 
 * Elle pousse plus loin le concept de condition en proposant une structure pour g�rer
 * les conditions dont on traite les instructions (en particulier imbriqu�es).
 * 
 * Les classes utilisant cette classe doivent :
 * - push une condition dans conditions � chaque fois qu'une fonction condOn(...) est appell�e
 * - impl�menter la fonction getAllElements qui donne tous les �l�ments � filtrer
 * 
 * En contrepartie, les fonctions peuvent utiliser la fonction getElementsFiltres() qui
 * renvoie tous les �l�ments qui satisfont les conditions actuelles.
 * 
 * 
 * @author Bruju
 *
 * @param <T> Le type sur lequel portent les conditions
 */
public abstract class StackedActionMaker<T> implements ActionMakerWithConditionalInterest {
	/**
	 * Liste des conditions actuellement trait�es
	 */
	protected MetaStack<T> conditions = new MetaStack<>();
	
	/**
	 * Permet d'obtenir la liste des �l�ments filtr�s par l'encha�nement courant de conditions
	 * @return La liste des �l�ments qui respectent les conditions �tablies
	 */
	public List<T> getElementsFiltres() {
		return conditions.filter(getAllElements());
	}
	
	/**
	 * Donne tous les �l�ments de la base de donn�es
	 * @return Une liste de tous les �l�ments non filtr�s
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
