package fr.bruju.rmeventreader.actionmakers.decrypter;

import java.io.IOException;

import fr.bruju.rmeventreader.actionmakers.actionner.ActionMaker;
import fr.bruju.rmeventreader.actionmakers.actionner.ConditionalActionMaker;
import fr.bruju.rmeventreader.actionmakers.decrypter.InterpreteurCherry;

/**
 * Décore l'action maker donné par un Conditional Action Maker et assigne un fichier.
 * <p>
 * Lorsque la méthode run() sera appelée, la lecture du fichier sera déclenchée.
 * 
 * @author Bruju
 *
 */
public class AutoEventFactory implements Runnable {
	/**
	 * ActionMaker de base
	 */
	private ActionMaker actionMaker;

	/**
	 * Nom du fichier
	 */
	private String filename;

	/**
	 * Construit une action dont le but est de traiter des fichiers au travers de l'actionMaker donné
	 * 
	 * @param actionMaker Le gestionnaire d'évènements
	 * @param filename Le nom du fichier qui sera traité
	 */
	public AutoEventFactory(ActionMaker actionMaker, String filename) {
		this.actionMaker = actionMaker;
		this.filename = filename;
	}

	@Override
	public void run() {
		ActionMaker conditionalActionMaker = new ConditionalActionMaker(actionMaker);
		InterpreteurCherry interpreter = new InterpreteurCherry(conditionalActionMaker);

		try {
			interpreter.inputFile(filename);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
