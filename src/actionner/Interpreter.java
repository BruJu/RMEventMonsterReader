package actionner;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import decrypter.Decrypter;

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
		FileReader fileReader = new FileReader(file);
		BufferedReader buffer = new BufferedReader(fileReader);
		
		while (true) {
			String line = buffer.readLine();
			
			if (line == null) {
				break;
			}
			
			interpreter(line);
		}
		
		buffer.close();
	}
	
	/**
	 * Déchiffre une liste de lignes
	 * @param lines La liste de lignes à déchiffrer
	 */
	public void inputLines(List<String> lines) {
		for (String line : lines) {
			interpreter(line);
		}
	}

	/**
	 * Déchiffre une ligne avec le decryper
	 * @param line La ligne à déchiffrer
	 */
	private void interpreter(String line) {
		decrypter.decript(line);
	}
}
