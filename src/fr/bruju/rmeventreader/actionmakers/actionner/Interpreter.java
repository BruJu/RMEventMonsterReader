package fr.bruju.rmeventreader.actionmakers.actionner;

import java.io.File;
import java.io.IOException;

/**
 * Classe permettant de déchiffrer des instructions RPG Maker 2003 et d'activer les fonctions de l'actionMaker donné.
 */
public interface Interpreter {

	/**
	 * Déchiffre un fichier
	 * 
	 * @param file Le fichier à déchiffrer
	 * @throws IOException
	 */
	void inputFile(File file) throws IOException;
}