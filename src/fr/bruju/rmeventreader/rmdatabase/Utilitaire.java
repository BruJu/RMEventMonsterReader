package fr.bruju.rmeventreader.rmdatabase;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fr.bruju.rmeventreader.filereader.FileReaderByLine;
import fr.bruju.rmeventreader.filereader.Recognizer;
import fr.bruju.rmeventreader.utilitaire.Pair;

public class Utilitaire {
	private Utilitaire() {
		
	}
	
	/**
	 * Lit le fichier de ressources et renvoie une liste de paires de valeurs lues
	 * @param nomDuFichier Le nom du fichier
	 * @return Une liste avec les valeurs lues à chaque lignes, ou null si le fichier n'existe pas, ou est invalide.  
	 */
	public static List<Pair<String, String>> lireFichierRessource(String nomDuFichier) {
		List<Pair<String, String>> associations = new ArrayList<>();
		
		try {
			FileReaderByLine.lireLeFichierSansCommentaires(nomDuFichier, ligne -> {				
				Pair<String, String> donnees = Recognizer.extractValues(ligne);
				
				if (donnees == null) {
					throw new NullPointerException();
				}
				
				associations.add(donnees);
			});
		} catch (IOException | NullPointerException e) {
			e.printStackTrace();
			return null;
		}
		
		return associations;
	}

	/**
	 * Crée une map associant nombre et chaîne à partir d'une liste de paires chaîne chaîne
	 * @param donnees La liste à convertir en association nombre - chaîne
	 * @return Une map associant au nombre à gauche de chaque paire la valeur à droite de la paire
	 */
	public static Map<Integer, String> creerMap(List<Pair<String, String>> donnees) {
		Map<Integer, String> map = new HashMap<>();
		
		donnees.forEach(paire -> {
			String cle = paire.getLeft();
			String valeur = paire.getRight();
			
			Integer cleParsee = Integer.decode(cle);
			
			map.put(cleParsee, valeur);
		});
		
		
		return map;
	}
	
}
