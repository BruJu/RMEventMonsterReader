package fr.bruju.rmeventreader.filereader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/**
 * Classe utilitaire permettant de lire des fichiers
 *  
 * @author Bruju
 *
 */
public class FileReaderByLine {
	private static final String COMMENTAIRE_STARTS_WITH = "//";
	
	public static boolean lectureFichierBrut(File file, Consumer<String> action) {
		try {
			FileReader fileReader = new FileReader(file);
			BufferedReader buffer = new BufferedReader(fileReader);
			String line;
	
			while (true) {
				line = buffer.readLine();
	
				if (line == null) {
					break;
				}
				
				action.accept(line);
			}
	
			buffer.close();
		} catch (IOException e) {
			return false;
		}
		
		return true;
	}

	/**
	 * Lit un fichier en utilisant l'actionneur donné. Considère que les lignes commençant par // sont des commentaires
	 * @param chemin Le chemin vers le fichier
	 * @param actionOnLine L'actionneur
	 * @return 
	 */
	public static boolean lectureFichierRessources(String chemin, ActionOnLine actionOnLine) {
		try {
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
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	
	/**
	 * Lit un fichier ressource (constitué de lignes avec des mots séparés par des espaces)
	 * @param chemin Le chemin vers le fichier
	 * @param nbArguments Le nombre de mots par ligne
	 * @return Une liste des mots de chaques lignes. Les mots sont stockés dans un tableau de String.
	 */
	public static List<String[]> lireFichier(String chemin, int nbArguments) {
		List<String[]> valeursLues = new ArrayList<>();
		
		boolean r = lectureFichierRessources(chemin, donnee -> valeursLues.add(splitter(donnee, nbArguments)));
		
		return r ? valeursLues : null;		
	}
	
	public static String[] splitter(String donnee, int nbArguments) {
		String[] split = donnee.split(" ", nbArguments);
		
		
		if (split.length < nbArguments) {
			String[] nouveauSplit = new String[nbArguments];
			
			for (int i = 0 ; i != split.length ; i++) {
				nouveauSplit[i] = split[i];
			}
			for (int i = split.length ; i != nouveauSplit.length ; i++) {
				nouveauSplit[i] = "";
			}
			
			split = nouveauSplit;
		}
		
		if (split.length != nbArguments) {
			throw new LigneNonReconnueException("Fichier non valide " + donnee);
		}
		
		return split;
	}
}
