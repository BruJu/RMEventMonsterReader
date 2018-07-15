package fr.bruju.rmeventreader.actionmakers.actionner;

import java.io.File;
import java.io.IOException;
import java.util.List;

import fr.bruju.rmeventreader.actionmakers.decrypter.Decrypter;
import fr.bruju.rmeventreader.filereader.FileReaderByLine;

/**
 * Classe permettant de déchiffrer des instructions RPG Maker 2003 et
 * d'activer les fonctions de l'actionMaker donné.
 */
public class Interpreter {
	/**
	 * Décrypteur utilisé
	 */
	private Decrypter decrypter;
	
	/**
	 * Crée un interpréteur avec l'actionMaker donné
	 * @param actionMaker L'objet qui traitera les actions déchiffrées
	 */
	public Interpreter (ActionMaker actionMaker) {
		decrypter = new Decrypter(actionMaker);
	}
	
	/**
	 * Déchiffre un fichier
	 * @param file Le fichier à déchiffrer
	 * @throws IOException
	 */
	public void inputFile(File file) throws IOException {
		FileReaderByLine.lireLeFichier(file, s -> decrypter.decript(s));
	}
	
	/**
	 * Déchiffre une liste de lignes
	 * @param lines La liste de lignes à déchiffrer
	 */
	public void inputLines(List<String> lines) {
		for (String line : lines) {
			decrypter.decript(line);
		}
	}
}
