package fr.bruju.rmeventreader.dictionnaires;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fr.bruju.rmeventreader.utilitaire.Utilitaire;

public class Encyclopedie {
	private static Encyclopedie instance;

	private Encyclopedie() {
	}

	public static Encyclopedie getInstance() {
		if (null == instance) {
			instance = new Encyclopedie();
		}
		return instance;
	}
	
	
	private static final String[][] dictionnairesConnus = {
			{ "PERSO", "actors", "bdd_heros"},
			{ "SWITCH", "switches", "bdd_switch"},
			{ "VARIABLE", "variables", "bdd_variables"},
			{ "OBJET", "items", "bdd_objets"}
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
			map.put(nomDuDictionnaire, new Dictionnaire(getAttributs(nomDuDictionnaire)));
		}
	}

	private String[] getAttributs(String nomDuDictionnaire) {
		for (String[] dicoConnu : dictionnairesConnus) {
			if (dicoConnu[0].equals(nomDuDictionnaire)) {
				return dicoConnu;
			}
		}
		
		return null;
	}
	
	
	public void ecrireRessource(String dossier) {
		for (String[] dicoConnu : dictionnairesConnus) {
			String nomDuDictionnaire = dicoConnu[0];
			assurerExistanceDictionnaire(nomDuDictionnaire);
			map.get(nomDuDictionnaire).ecrireFichier(dossier);
		}
	}

	private class Dictionnaire {
		private List<String> donneesExtraites;
		private final String fichier;

		private Dictionnaire(String[] attributs) {
			donneesExtraites = LecteurDeLCF$.getInstance().getListeDeNoms(attributs[1]);
			fichier = attributs[2];
		}

		private String extraire(int index) {
			return donneesExtraites.get(index - 1);
		}
		


		public void ecrireFichier(String dossier) {
			String chemin = dossier + fichier + ".txt";
			
			StringBuilder sb = new StringBuilder();
			for (String valeur : donneesExtraites) {
				sb.append(valeur + "\n");
			}
			
			Utilitaire.Fichier_Ecrire(chemin, sb.toString());
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
