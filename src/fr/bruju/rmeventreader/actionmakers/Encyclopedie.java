package fr.bruju.rmeventreader.actionmakers;

import java.io.File;
import java.io.IOException;
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

	public String get(String nomDuDictionnaire, int index) {
		assurerExistanceDictionnaire(nomDuDictionnaire);
		return map.get(nomDuDictionnaire).extraire(index);
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

			try {
				FileReaderByLine.lireLeFichier(fichier, valeursLues::add);
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			donneesExtraites = valeursLues.toArray(new String[0]);
		}

		private String extraire(int index) {
			return donneesExtraites[index - premierIndex];
		}
	}
}
