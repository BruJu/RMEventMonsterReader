package fr.bruju.rmeventreader.implementation.monsterlist.autotraitement;

import java.io.File;
import java.io.IOException;

import fr.bruju.rmeventreader.actionmakers.actionner.ActionMaker;
import fr.bruju.rmeventreader.actionmakers.actionner.ConditionalActionMaker;
import fr.bruju.rmeventreader.actionmakers.actionner.Interpreter;

/**
 * Action automatique utilisant un Action Maker pour traiter un fichier.
 * L'ActionMaker donné sera traité au travers d'un ConditionalActionMaker.
 * 
 * @author Bruju
 *
 */
public class AutoActionMaker implements ActionAutomatique {
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
	public void faire() {
		ActionMaker conditionalActionMaker = new ConditionalActionMaker(actionMaker);
		Interpreter interpreter = new Interpreter(conditionalActionMaker);
		
		try {
			interpreter.inputFile(new File(filename));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
