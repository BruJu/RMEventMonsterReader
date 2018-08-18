package fr.bruju.rmeventreader.actionmakers.xml;

import java.io.IOException;

import fr.bruju.rmeventreader.actionmakers.actionner.ActionMaker;
import fr.bruju.rmeventreader.actionmakers.actionner.ConditionalActionMaker;

/**
 * Décore l'action maker donné par un Conditional Action Maker et assigne un fichier.
 * <p>
 * Lorsque la méthode run() sera appelée, la lecture du fichier sera déclenchée.
 * 
 * @author Bruju
 *
 */
class AutoLibLcfXML implements Runnable {
	/**
	 * ActionMaker de base
	 */
	private ActionMaker actionMaker;

	/**
	 * Nom du fichier
	 */
	private String filename;

	private int idEvent;

	private int idPage;

	/**
	 * Construit une action dont le but est de traiter des fichiers au travers de l'actionMaker donné
	 * 
	 * @param actionMaker Le gestionnaire d'évènements
	 * @param filename Le nom du fichier qui sera traité
	 */
	public AutoLibLcfXML(ActionMaker actionMaker, String filename, int idEvent, int idPage) {
		this.actionMaker = actionMaker;
		this.filename = filename;
		this.idEvent = idEvent;
		this.idPage = idPage;
	}

	@Override
	public void run() {
		ActionMaker conditionalActionMaker = new ConditionalActionMaker(actionMaker);
		InterpreterMapXML interpreter = new InterpreterMapXML(conditionalActionMaker);

		try {
			interpreter.inputFile(filename, idEvent, idPage);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
