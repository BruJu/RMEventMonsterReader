package fr.bruju.rmeventreader.utilitaire;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.function.Consumer;
import java.util.stream.Stream;

import fr.bruju.rmeventreader.utilitaire.Utilitaire;

/**
 * Classe utilitaire permettant de lire des fichiers
 *  
 * @author Bruju
 *
 */
public class LecteurDeFichiersLigneParLigne {
	private static final String DEBUT_COMMENTAIRE = "//";

	/**
	 * Lit le fichier dont le nom est passé en argument et applique le consommateur donné. Ignore les lignes vides
	 * et les lignes commençant par "//"
	 * @param chemin Le chemin vers le fichier
	 * @param action L'action à réaliser sur les lignes non ignorées
	 * @return Vrai si aucune erreur n'a été jetée
	 */
	public static boolean lectureFichierRessources(String chemin, Consumer<String> action) {
		try (Stream<String> flux = Files.lines(Paths.get(chemin))) {
			flux.filter(LecteurDeFichiersLigneParLigne::filtrer)
				.forEach(action);
		} catch (IOException e) {
			return false;
		}
		
		return true;
	}
	
	/**
	 * Renvoie vrai si la ligne n'est pas vide et ne commence par par //
	 * @param ligne La ligne
	 * @return Vrai si la ligne est non vide et ne commence pas par //
	 */
	private static boolean filtrer(String ligne) {
		return !ligne.equals("") && !ligne.startsWith(DEBUT_COMMENTAIRE);
	}
	
	/**
	 * Sépare la chaîne donnée pour avoir un tableau d'exactement nbArguments. Le tableau en séparant les parties
	 * séparées d'un espace. La séparation se fait de gauche à droite. Si il n'y en a pas assez des chaînes vides
	 * sont ajoutées.
	 * @param donnee La donnée à fragmenter
	 * @param nbArguments Le nombre de chaînes voulues
	 * @return Un tableau avec donnee fragmenté par les espaces
	 */
	public static String[] diviser(String donnee, int nbArguments) {
		String[] split = donnee.split(" ", nbArguments);
		Utilitaire.Arrays_aggrandir(split, nbArguments, LecteurDeFichiersLigneParLigne::getChaineVide);
		return split;
	}
	
	/** Donne une chaîne vide */
	private static String getChaineVide() {
		return "";
	}
}
