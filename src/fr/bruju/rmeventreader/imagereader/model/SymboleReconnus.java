package fr.bruju.rmeventreader.imagereader.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import fr.bruju.rmeventreader.utilitaire.LecteurDeFichiersLigneParLigne;

/**
 * Cette classe récupère la liste des motifs à reconnaître.
 */
public class SymboleReconnus {
	/**
	 * Chemin vers la liste des motifs connus
	 */
	private static final String CHEMIN_MOTIFS_CONNUS = "metaressources/ocr/motifsconnus.txt";

	/**
	 * Renvoie la liste des motifs déjà connus, qui sont dans metaressources/ocr/motifsconnus.txt
	 */
	public List<Motif> getMotifs() {
		List<Motif> motifs = new ArrayList<>();

		boolean m = LecteurDeFichiersLigneParLigne.lectureFichierRessources(CHEMIN_MOTIFS_CONNUS, line -> {
				Scanner scanner = new Scanner(line);

				String chaine = scanner.next();
				
				List<Integer> valeursMotif = new ArrayList<>();

				while (scanner.hasNextInt()) {
					valeursMotif.add(scanner.nextInt());
				}

				scanner.close();
				
				
				int[] valeurs = valeursMotif.stream().mapToInt(valeur -> valeur).toArray();

				motifs.add(new Motif(chaine, valeurs));
			});

		return m ? motifs : null;
	}
}
