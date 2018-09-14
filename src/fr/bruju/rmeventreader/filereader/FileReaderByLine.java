package fr.bruju.rmeventreader.filereader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import fr.bruju.rmeventreader.utilitaire.Utilitaire;

/**
 * Classe utilitaire permettant de lire des fichiers
 *  
 * @author Bruju
 *
 */
public class FileReaderByLine {
	private static final String COMMENTAIRE_STARTS_WITH = "//";
	
	/**
	 * Lit le fichier et applique à chaque ligne la fonction action
	 * @param file Le fichier à lire
	 * @param action L'action à appliquer à chaque ligne
	 * @return Vrai si la lecture s'est bien déroulée. Faux si il y a eu une erreur (IOException levée en cours de
	 * lecture)
	 */
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
		return lectureFichierBrut(new File(chemin), ligne -> {
			if (!ligne.equals("") && !ligne.startsWith(COMMENTAIRE_STARTS_WITH)) {
				actionOnLine.read(ligne);
			}
		});
	}
	
	/**
	 * Lit un fichier ressource (constitué de lignes avec des mots séparés par des espaces)
	 * @param chemin Le chemin vers le fichier
	 * @param nbArguments Le nombre de mots par ligne
	 * @return Une liste des mots de chaques lignes. Les mots sont stockés dans un tableau de String.
	 */
	private static List<String[]> lireFichier(String chemin, int nbArguments) {
		List<String[]> valeursLues = new ArrayList<>();
		boolean r = lectureFichierRessources(chemin, donnee -> valeursLues.add(splitter(donnee, nbArguments)));
		return r ? valeursLues : null;
	}
	
	/**
	 * Sépare la chaîne donnée pour avoir un tableau d'exactement nbArguments. Le tableau en séparant les parties
	 * séparées d'un espace. La séparation se fait de gauche à droite. Si il n'y en a pas assez des chaînes vides
	 * sont ajoutées.
	 * @param donnee La donnée à fragmenter
	 * @param nbArguments Le nombre de chaînes voulues
	 * @return Un tableau avec donnee fragmenté par les espaces
	 */
	public static String[] splitter(String donnee, int nbArguments) {
		String[] split = donnee.split(" ", nbArguments);
		Utilitaire.Arrays_aggrandir(split, nbArguments, FileReaderByLine::getChaineVide);
		return split;
	}
	
	/** Donne une chaîne vide */
	private static String getChaineVide() {
		return "";
	}
}
