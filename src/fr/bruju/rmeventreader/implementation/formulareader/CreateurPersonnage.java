package fr.bruju.rmeventreader.implementation.formulareader;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fr.bruju.rmeventreader.actionmakers.decrypter.Recognizer;
import fr.bruju.rmeventreader.filereader.FileReaderByLine;
import fr.bruju.rmeventreader.implementation.formulareader.model.Personnage;
import fr.bruju.rmeventreader.implementation.formulareader.model.Statistique;
import fr.bruju.rmeventreader.utilitaire.Pair;

public class CreateurPersonnage {
	private static final String CHEMIN_STATS = "ressources/statistiques/";
	private static final String PATTERN = "_ _";

	public Personnage creerPersonnage(String nom) {
		Map<Integer, Statistique> map = new HashMap<>();

		Personnage personnage = new Personnage(nom, map);

		try {
			FileReaderByLine.lireLeFichier(new File(CHEMIN_STATS + nom + ".txt"), ligne -> {
				List<String> args = Recognizer.tryPattern(PATTERN, ligne);

				if (args == null) {
					return;
				}

				Statistique stat = Statistique.reconnaitre(args.get(0));

				if (stat == null) {
					return;
				}

				int idVariable = Integer.parseInt(args.get(1));

				map.put(idVariable, stat);
			});
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}

		return personnage;
	}

	public static List<Personnage> creerTousLesPersonnages() {
		List<Personnage> liste = new ArrayList<>();

		CreateurPersonnage cp = new CreateurPersonnage();

		File repertoire = new File(CHEMIN_STATS);

		for (String fichier : repertoire.list()) {
			String nomSansTxt = fichier.substring(0, fichier.length() - 4);

			Personnage perso = cp.creerPersonnage(nomSansTxt);

			if (perso != null) {
				liste.add(perso);
			}
		}

		return liste;
	}

	private static Map<Integer, Pair<Personnage, Statistique>> map = null;

	public static Map<Integer, Pair<Personnage, Statistique>> mapStatistiques(List<Personnage> personnages) {
		Map<Integer, Pair<Personnage, Statistique>> map = new HashMap<>();

		personnages.forEach(personnage -> personnage.getStatistiques().forEach(
				(idVariable, statistiqueAssocie) -> map.put(idVariable, new Pair<>(personnage, statistiqueAssocie)))

		);

		return map;
	}

	public static Map<Integer, Pair<Personnage, Statistique>> getMap() {
		if (map == null) {
			map = mapStatistiques(creerTousLesPersonnages());
		}

		return map;
	}
}
