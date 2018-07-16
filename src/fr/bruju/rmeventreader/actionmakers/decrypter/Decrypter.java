package fr.bruju.rmeventreader.actionmakers.decrypter;

import java.util.List;

import fr.bruju.rmeventreader.actionmakers.actionner.ActionMaker;
import fr.bruju.rmeventreader.actionmakers.decrypter.convertisseurs.Action;
import fr.bruju.rmeventreader.filereader.LigneNonReconnueException;

/**
 * Classe permettant de d�crypter des lignes et de d�clencher les actions
 * qui leur correspond.
 */
public class Decrypter {
	/**
	 * Liste des pattern � d�chiffrer
	 */
	private static List<Action> patterns = AssociationChaineInstruction.bookMaker();
	
	
	
	/**
	 * Gestionnaire d'action
	 */
	private ActionMaker actionMaker;

	/**
	 * Cr�e un d�crypteur de lignes
	 * @param actionMaker L'objet qui se chargera d'appliquer les actions
	 */
	public Decrypter(ActionMaker actionMaker) {
		this.actionMaker = actionMaker;
	}

	/**
	 * D�chiffre une ligne et appelle l'action associ�e
	 * @param line La ligne � d�chiffrer
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
}
