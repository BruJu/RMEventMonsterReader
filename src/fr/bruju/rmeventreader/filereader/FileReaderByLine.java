package fr.bruju.rmeventreader.filereader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe utilitaire permettant de lire des fichiers
 *  
 * @author Bruju
 *
 */
public class FileReaderByLine {
	private static final String COMMENTAIRE_STARTS_WITH = "//";
	
	/**
	 * Lit un fichier en utilisant l'actionneur utilisé
	 * @param file Le fichier
	 * @param actionOnLine L'actionneur
	 * @throws IOException
	 */
	public static void lireLeFichier(File file, ActionOnLine actionOnLine) throws IOException {
		FileReader fileReader = new FileReader(file);
		BufferedReader buffer = new BufferedReader(fileReader);
		String line;

		while (true) {
			line = buffer.readLine();

			if (line == null) {
				break;
			}

			if (!line.equals("")) {
				actionOnLine.read(line);
			}
		}

		buffer.close();
	}
	

	/**
	 * Lit un fichier en utilisant l'actionneur donné. Considère que les lignes commençant par // sont des commentaires
	 * @param chemin Le chemin vers le fichier
	 * @param actionOnLine L'actionneur
	 * @throws IOException
	 */
	public static void lireLeFichierSansCommentaires(String chemin, ActionOnLine actionOnLine) throws IOException {
		FileReader fileReader = new FileReader(new File(chemin));
		BufferedReader buffer = new BufferedReader(fileReader);
		String line;

		while (true) {
			line = buffer.readLine();

			if (line == null) {
				break;
			}

			if (!line.equals("") && !line.startsWith(COMMENTAIRE_STARTS_WITH)) {
				actionOnLine.read(line);
			}
		}

		buffer.close();
	}
	
	
	/**
	 * Lit un fichier ressource (constitué de lignes avec des mots séparés par des espaces)
	 * @param chemin Le chemin vers le fichier
	 * @param nbArguments Le nombre de mots par ligne
	 * @return Une liste des mots de chaques lignes. Les mots sont stockés dans un tableau de String.
	 * @throws IOException
	 */
	public static List<String[]> lireFichier(String chemin, int nbArguments) throws IOException {
		List<String[]> valeursLues = new ArrayList<>();
		
		lireLeFichierSansCommentaires(chemin, donnee -> valeursLues.add(splitter(donnee, nbArguments)));
		
		return valeursLues;
	}
	
	public static String[] splitter(String donnee, int nbArguments) {
		String[] split = donnee.split(" ");
		
		if (split.length > nbArguments) {
			String[] nouveauSplit = new String[nbArguments];
			
			for (int i = nbArguments ; i != split.length ; i++) {
				split[nbArguments-1] += " " + split[i];
			}
			
			for (int i = 0 ; i != nouveauSplit.length ; i++) {
				nouveauSplit[i] = split[i];
			}
			
			split = nouveauSplit;
		}
		
		if (split.length != nbArguments) {
			throw new LigneNonReconnueException("Fichier non valide " + donnee);
		}
		
		return split;
	}
}
