package actionner;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import decrypter.Decrypter;

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
	 * D�chiffre une liste de lignes
	 * @param lines La liste de lignes � d�chiffrer
	 */
	public void inputLines(List<String> lines) {
		for (String line : lines) {
			interpreter(line);
		}
	}

	/**
	 * D�chiffre une ligne avec le decryper
	 * @param line La ligne � d�chiffrer
	 */
	private void interpreter(String line) {
		decrypter.decript(line);
	}
}
