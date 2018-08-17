package fr.bruju.rmeventreader.actionmakers.xml;

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
public class AutoLibLcfXMLCache implements Runnable {
	/**
	 * ActionMaker de base
	 */
	private ActionMaker actionMaker;

	/**
	 * Nom du fichier
	 */
	private String dossierCache;

	public final int idEvent;

	public final int idPage;
	
	public final int idMap;

	/**
	 * Construit une action dont le but est de traiter des fichiers au travers de l'actionMaker donné
	 * 
	 * @param actionMaker Le gestionnaire d'évènements
	 * @param filename Le nom du fichier qui sera traité
	 */
	public AutoLibLcfXMLCache(ActionMaker actionMaker, String dossierCache, int idMap, int idEvent, int idPage) {
		this.actionMaker = actionMaker;
		this.dossierCache = dossierCache;
		this.idEvent = idEvent;
		this.idPage = idPage;
		this.idMap = idMap;
	}

	@Override
	public void run() {
		ActionMaker conditionalActionMaker = new ConditionalActionMaker(actionMaker);
		InterpreterMapXMLCache interpreter = new InterpreterMapXMLCache(conditionalActionMaker);

		System.out.println(idMap + " " + idEvent + " " + idPage);
		interpreter.inputFile(dossierCache, idMap, idEvent, idPage);
	}
}
