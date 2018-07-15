package fr.bruju.rmeventreader.actionmakers.decrypter;

import java.util.List;

import fr.bruju.rmeventreader.actionmakers.actionner.ActionMaker;
import fr.bruju.rmeventreader.actionmakers.decrypter.convertisseurs.Action;

/**
 * Classe permettant de décrypter des lignes et de déclencher les actions
 * qui leur correspond.
 */
public class Decrypter {
	/**
	 * Liste des pattern à déchiffrer
	 */
	private static List<Action> patterns = AssociationChaineInstruction.bookMaker();
	
	
	
	/**
	 * Gestionnaire d'action
	 */
	private ActionMaker actionMaker;

	/**
	 * Crée un décrypteur de lignes
	 * @param actionMaker L'objet qui se chargera d'appliquer les actions
	 */
	public Decrypter(ActionMaker actionMaker) {
		this.actionMaker = actionMaker;
	}

	/**
	 * Déchiffre une ligne et appelle l'action associée
	 * @param line La ligne à déchiffrer
	 */
	public void decript(String line) {
		for (Action pattern : patterns) {
			List<String> argumentsReconnus = Recognizer.tryPattern(pattern.getPattern(), line);
			
			if (argumentsReconnus != null) {
				pattern.faire(actionMaker, argumentsReconnus);
				return;
			}
		}
		
		throw new LigneNonReconnueException(line);
	}
	
	
	/**
	 * Exception jetée lorsqu'une ligne n'est pas reconnue
	 */
	private class LigneNonReconnueException extends RuntimeException {
		/**
		 * Un serial id unique
		 */
		private static final long serialVersionUID = -6801007137729905515L;
		
		/**
		 * Déclare une LigneNonReconnueException avec la ligne donnée
		 * @param ligne La ligne qui n'a pas pu être reconnue
		 */
		public LigneNonReconnueException(String ligne) {
			super("LigneNonReconnueException : " + ligne);
		}
	}
}
