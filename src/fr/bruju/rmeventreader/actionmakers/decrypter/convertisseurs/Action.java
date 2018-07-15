package fr.bruju.rmeventreader.actionmakers.decrypter.convertisseurs;

import java.util.List;

import fr.bruju.rmeventreader.actionmakers.actionner.ActionMaker;

/**
 * Une action est d�finie comme �tant un pattern � reconna�tre, et une action � faire
 * si le pattern est reconnu.
 */
public interface Action {
	/**
	 * Pattern � reconna�tre
	 * @return Le pattern � reconna�tre
	 */
	public String getPattern();
	
	/**
	 * Traduit les arguments reconnus par le pattern, et appelle la m�thode ad�quate
	 * de actionMaker
	 * @param actionMaker Le gestionnaire d'action fourni par l'utilisateur
	 * @param arguments La liste des arguments reconnus dans le pattern
	 */
	public void faire(ActionMaker actionMaker, List<String> arguments);
}

