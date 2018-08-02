package fr.bruju.rmeventreader.actionmakers;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fr.bruju.rmeventreader.filereader.FileReaderByLine;
import fr.bruju.rmeventreader.filereader.LigneNonReconnueException;
import fr.bruju.rmeventreader.filereader.Recognizer;
import fr.bruju.rmeventreader.utilitaire.Container;
import fr.bruju.rmeventreader.utilitaire.Pair;

public class Encyclopedie {
	private static final String[][] dictionnairesConnus = {
			{ "PERSO", "ressources/basededonnees/noms/personnages.txt"},
			{ "SWITCH", "ressources/basededonnees/noms/interrupteurs.txt"},
			{ "VARIABLE", "ressources/basededonnees/noms/variables.txt"},
			{ "OBJET", "ressources/basededonnees/noms/objets.txt"}
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

			Container<Integer> premierIndexVu = new Container<>();
			Container<Integer> dernierIndexVu = new Container<>();
			List<String> valeursLues = new ArrayList<>();

			premierIndexVu.item = null;

			try {
				FileReaderByLine.lireLeFichier(fichier, ligne -> {
					Pair<String, String> paire = Recognizer.extractValues(ligne);

					Integer numero = Integer.decode(paire.getLeft());
					String valeur = paire.getRight();

					if (premierIndexVu.item == null) {
						premierIndexVu.item = numero;
					} else {
						if (!(dernierIndexVu.item.equals(numero - 1))) {
							throw new LigneNonReconnueException(
									ligne + " n'a pas un l'id attendu " + dernierIndexVu.item);
						}
					}

					dernierIndexVu.item = numero;
					valeursLues.add(valeur);
				});
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			donneesExtraites = valeursLues.toArray(new String[0]);
			premierIndex = premierIndexVu.item;
		}

		private String extraire(int index) {
			return donneesExtraites[index - premierIndex];
		}
	}
}
