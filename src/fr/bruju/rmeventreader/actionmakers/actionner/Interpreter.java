package fr.bruju.rmeventreader.actionmakers.actionner;

import java.io.File;
import java.io.IOException;
import java.util.List;

import fr.bruju.rmeventreader.actionmakers.decrypter.Decrypter;
import fr.bruju.rmeventreader.filereader.FileReaderByLine;

/**
 * Classe permettant de d�chiffrer des instructions RPG Maker 2003 et
 * d'activer les fonctions de l'actionMaker donn�.
 */
public class Interpreter {
	/**
	 * D�crypteur utilis�
	 */
	private Decrypter decrypter;
	
	/**
	 * Cr�e un interpr�teur avec l'actionMaker donn�
	 * @param actionMaker L'objet qui traitera les actions d�chiffr�es
	 */
	public Interpreter (ActionMaker actionMaker) {
		decrypter = new Decrypter(actionMaker);
	}
	
	/**
	 * D�chiffre un fichier
	 * @param file Le fichier � d�chiffrer
	 * @throws IOException
	 */
	public void inputFile(File file) throws IOException {
		FileReaderByLine.lireLeFichier(file, s -> decrypter.decript(s));
	}
	
	/**
	 * D�chiffre une liste de lignes
	 * @param lines La liste de lignes � d�chiffrer
	 */
	public void inputLines(List<String> lines) {
		for (String line : lines) {
			decrypter.decript(line);
		}
	}
}
