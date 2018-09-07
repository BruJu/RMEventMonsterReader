package fr.bruju.rmeventreader.actionmakers;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fr.bruju.rmeventreader.filereader.FileReaderByLine;

public class Encyclopedie {
	private static final String[][] dictionnairesConnus = {
			{ "PERSO", "ressources_gen/bdd_heros.txt"},
			{ "SWITCH", "ressources_gen/bdd_switch.txt"},
			{ "VARIABLE", "ressources_gen/bdd_variables.txt"},
			{ "OBJET", "ressources_gen/bdd_objets.txt"}
	};

	private Map<String, Dictionnaire> map = new HashMap<>();

	/**
	 * 
	 * @param nomDuDictionnaire PERSO + SWITCH + VARIABLE + OBJET
	 * @param index
	 * @return
	 */
	public String get(String nomDuDictionnaire, int index) {
		assurerExistanceDictionnaire(nomDuDictionnaire);
		return map.get(nomDuDictionnaire).extraire(index);
	}
	
	public String getSansSymbole(String nomDuDictionnaire, int index) {
		assurerExistanceDictionnaire(nomDuDictionnaire);
		return map.get(nomDuDictionnaire).extraireSansSymbole(index);
	}

	private void assurerExistanceDictionnaire(String nomDuDictionnaire) {
		if (map.get(nomDuDictionnaire) == null) {
			map.put(nomDuDictionnaire, new Dictionnaire(getChemin(nomDuDictionnaire)));
		}
	}

	private String getChemin(String nomDuDictionnaire) {
		for (String[] dicoConnu : dictionnairesConnus) {
			if (dicoConnu[0].equals(nomDuDictionnaire)) {
				return dicoConnu[1];
			}
		}
		
		return null;
	}

	private class Dictionnaire {
		private String[] donneesExtraites;
		private int premierIndex;

		private Dictionnaire(String chemin) {
			lireFichier(chemin);
		}

		private void lireFichier(String chemin) {
			File fichier = new File(chemin);
			
			List<String> valeursLues = new ArrayList<>();
			
			FileReaderByLine.lectureFichierBrut(fichier, valeursLues::add);
			
			donneesExtraites = valeursLues.toArray(new String[0]);
			premierIndex = 1;
		}

		private String extraire(int index) {
			return donneesExtraites[index - premierIndex];
		}
		
		/**
		 * Renvoie " " si l'index est 0
		 * <br>
		 * Sinon renvoie la chaîne à la valeur index - 1
		 * <br>
		 * Si il y a un symbole (chaîne commencant par $ et suivi d'un caractère), il est retiré
		 * @param index La ligne voulue du dictionnaire
		 * @return Une représentation de la donnée à la ligne index
		 */
		private String extraireSansSymbole(int index) {
			if (index == 0)
				return " ";
			
			String valeur = extraire(index);
			
			if (valeur.startsWith("$") && valeur.length() >= 2)
				valeur = valeur.substring(2);
			
			return valeur;
		}
	}
}
