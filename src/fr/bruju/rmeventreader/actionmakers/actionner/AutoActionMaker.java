package fr.bruju.rmeventreader.actionmakers.actionner;

import java.io.File;
import java.io.IOException;
import java.util.function.Function;

import fr.bruju.rmeventreader.actionmakers.decrypter.InterpreteurCherry;

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

	/** Instancieur d'interpreteur */
	private Function<ActionMaker, InterpreteurCherry> supplier;

	/**
	 * Construit une action dont le but est de traiter des fichiers au travers de l'actionMaker donné
	 * 
	 * @param actionMaker Le gestionnaire d'évènements
	 * @param filename Le nom du fichier qui sera traité
	 */
	public AutoActionMaker(ActionMaker actionMaker, String filename) {
		this.actionMaker = actionMaker;
		this.filename = filename;
		this.supplier = InterpreteurCherry::new;
	}

	/**
	 * Construit une action dont le but est de traiter des fichiers au travers de l'actionMaker donné
	 * 
	 * @param actionMaker Le gestionnaire d'évènements
	 * @param filename Le nom du fichier qui sera traité
	 * @param instancieur La fonction permettant d'instancier un interpreteur pour le fichier
	 */
	public AutoActionMaker(ActionMaker actionMaker, String filename,
			Function<ActionMaker, InterpreteurCherry> instancieur) {
		this.actionMaker = actionMaker;
		this.filename = filename;
		this.supplier = instancieur;
	}

	@Override
	public void run() {
		ActionMaker conditionalActionMaker = new ConditionalActionMaker(actionMaker);
		Interpreter interpreter = supplier.apply(conditionalActionMaker);

		try {
			interpreter.inputFile(new File(filename));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
