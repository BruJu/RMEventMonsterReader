package fr.bruju.rmeventreader.implementation.monsterlist;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fr.bruju.rmeventreader.actionmakers.decrypter.Recognizer;
import fr.bruju.rmeventreader.filereader.FileReaderByLine;
import fr.bruju.rmeventreader.implementation.monsterlist.metier.Monstre;
import fr.bruju.rmeventreader.implementation.monsterlist.metier.Monstre.Remplacement;

public class Correspondeur {
	private Map<String, String> map = new HashMap<>();

	public void lireFichier(File file) throws IOException {
		String pattern = "_ _";

		FileReaderByLine.lireLeFichier(file, line -> {
			List<String> chaines = Recognizer.tryPattern(pattern, line);

			map.put(chaines.get(0), chaines.get(1));
		});
	}

	public String get(String cle) {
		return map.get(cle);
	}

	public String get(int cle) {
		return map.get(Integer.toString(cle));
	}

	public void searchAndReplace(List<Monstre> monstres, Remplacement remplaceur) {
		for (Monstre monstre : monstres) {
			String id = remplaceur.get(monstre);

			String valeur = map.get(id);

			if (valeur != null) {
				remplaceur.set(monstre, valeur);
			}
		}
	}

}
