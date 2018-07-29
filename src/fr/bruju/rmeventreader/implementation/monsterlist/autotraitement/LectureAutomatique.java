package fr.bruju.rmeventreader.implementation.monsterlist.autotraitement;

import java.io.File;
import java.io.IOException;

import fr.bruju.rmeventreader.filereader.ActionOnLine;
import fr.bruju.rmeventreader.filereader.FileReaderByLine;

/**
 * Action consistant à lire un fichier en utilisant une action donnée lorsqu'on lit une ligne
 * 
 * @author Bruju
 *
 */
public class LectureAutomatique implements Runnable {
	/**
	 * Action réaliser à la lecture d'une ligne
	 */
	private ActionOnLine correcteur; 
	
	/**
	 * Nom du fichier
	 */
	private String filename;
	
	/**
	 * Construit une action qui traitera les lignes du fichier
	 * @param action Classe traitant les lignes données
	 * @param filename Le nom du fichier
	 */
	public LectureAutomatique(ActionOnLine action, String filename) {
		this.correcteur = action;
		this.filename = filename;
	}

	@Override
	public void run() {
		try {
			FileReaderByLine.lireLeFichier(new File(filename), correcteur);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
