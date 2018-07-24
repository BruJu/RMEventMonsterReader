package fr.bruju.rmeventreader.implementation.formulatracker.contexte;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fr.bruju.rmeventreader.filereader.FileReaderByLine;
import fr.bruju.rmeventreader.filereader.LigneNonReconnueException;
import fr.bruju.rmeventreader.filereader.Recognizer;
import fr.bruju.rmeventreader.implementation.formulatracker.contexte.personnage.PersonnageReel;

public class Personnages {
	Map<String, PersonnageReel> personnagesReels = new HashMap<>();

	public void lirePersonnagesDansFichier(String chemin) throws IOException {
		String pattern = "_ _ _";

		FileReaderByLine.lireLeFichierSansCommentaires(chemin, ligne -> {
			List<String> donnees = Recognizer.tryPattern(pattern, ligne);

			if (donnees == null) {
				throw new LigneNonReconnueException("");
			}

			String nomPersonnage = donnees.get(0);
			String nomStatistique = donnees.get(1);
			Integer numeroVariable = Integer.decode(donnees.get(2));

			injecter(nomPersonnage, nomStatistique, numeroVariable);
		});
	}

	public Collection<PersonnageReel> getPersonnages() {
		return personnagesReels.values();
	}

	private void injecter(String nomPersonnage, String nomStatistique, Integer numeroVariable) {
		PersonnageReel perso = personnagesReels.get(nomPersonnage);

		if (perso == null) {
			perso = new PersonnageReel(nomPersonnage);
			personnagesReels.put(nomPersonnage, perso);
		}

		perso.addStatistique(nomStatistique, numeroVariable);
	}

	/* =================
	 * AFFICHAGE CONSOLE
	 * ================= */

	public void afficherToutsLesStatistiques() {
		personnagesReels.values().stream().flatMap(personnage -> personnage.getStatistiques().values().stream())
				.forEach(stat -> System.out.println(stat.getPossesseur().getNom()
											+ " " + stat.getNom()
											+ " " + stat.getPosition()));
	}

}
