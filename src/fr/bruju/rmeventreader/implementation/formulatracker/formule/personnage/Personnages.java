package fr.bruju.rmeventreader.implementation.formulatracker.formule.personnage;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import fr.bruju.rmeventreader.filereader.FileReaderByLine;
import fr.bruju.rmeventreader.filereader.LigneNonReconnueException;

public class Personnages {
	Map<String, PersonnageReel> personnagesReels = new HashMap<>();

	public void lirePersonnagesDansFichier(String chemin) throws IOException {
		FileReaderByLine.lireLeFichierSansCommentaires(chemin, ligne -> {
			String[] donnees = ligne.split(" ");

			if (donnees == null || donnees.length != 3) {
				throw new LigneNonReconnueException("");
			}

			String nomPersonnage = donnees[0];
			String nomStatistique = donnees[1];
			
			boolean estPropriete = donnees[2].charAt(0) == 'S';
			
			Integer numeroVariable = Integer.decode((estPropriete ? donnees[2].substring(1) : donnees[2]));

			injecter(nomPersonnage, nomStatistique, numeroVariable, !estPropriete);
		});
	}

	public Collection<PersonnageReel> getPersonnages() {
		return personnagesReels.values();
	}

	private void injecter(String nomPersonnage, String nomStatistique, Integer numeroVariable, boolean estStat) {
		PersonnageReel perso = personnagesReels.get(nomPersonnage);

		if (perso == null) {
			perso = new PersonnageReel(nomPersonnage);
			personnagesReels.put(nomPersonnage, perso);
		}

		if (estStat)
			perso.addStatistique(nomStatistique, numeroVariable);
		else
			perso.addPropriete(nomStatistique, numeroVariable);
	}

	/* =================
	 * AFFICHAGE CONSOLE
	 * ================= */

	public void afficherToutsLesStatistiques() {
		personnagesReels.values().stream().flatMap(personnage -> personnage.getStatistiques().values().stream())
				.forEach(stat -> System.out.println(stat.possesseur.getNom()
											+ " " + stat.nom
											+ " " + stat.position));
	}

}
