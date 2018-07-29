package fr.bruju.rmeventreader.actionmakers.actionner;

import java.io.File;
import java.io.IOException;

/**
 * Décore l'action maker donné par un Conditional Action Maker et assigne un fichier.
 * <p>
 * Lorsque la méthode run() sera appelée, la lecture du fichier sera déclenchée.
 * 
 * @author Bruju
 *
 */
public class AutoActionMaker implements Runnable {
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
	 * @param actionMaker Le gestionnaire d'évènements
	 * @param filename Le nom du fichier qui sera traité
	 */
	public AutoActionMaker(ActionMaker actionMaker, String filename) {
		this.actionMaker = actionMaker;
		this.filename = filename;
	}
	
	@Override
	public void run() {
		ActionMaker conditionalActionMaker = new ConditionalActionMaker(actionMaker);
		Interpreter interpreter = new Interpreter(conditionalActionMaker);
		
		try {
			interpreter.inputFile(new File(filename));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
