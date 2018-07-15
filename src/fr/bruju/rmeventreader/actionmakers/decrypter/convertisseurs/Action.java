package fr.bruju.rmeventreader.actionmakers.decrypter.convertisseurs;

import java.util.List;

import fr.bruju.rmeventreader.actionmakers.actionner.ActionMaker;

/**
 * Une action est définie comme étant un pattern à reconnaître, et une action à faire
 * si le pattern est reconnu.
 */
public interface Action {
	/**
	 * Pattern à reconnaître
	 * @return Le pattern à reconnaître
	 */
	public String getPattern();
	
	/**
	 * Traduit les arguments reconnus par le pattern, et appelle la méthode adéquate
	 * de actionMaker
	 * @param actionMaker Le gestionnaire d'action fourni par l'utilisateur
	 * @param arguments La liste des arguments reconnus dans le pattern
	 */
	public void faire(ActionMaker actionMaker, List<String> arguments);
}

