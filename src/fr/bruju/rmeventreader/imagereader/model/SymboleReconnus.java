package fr.bruju.rmeventreader.imagereader.model;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import fr.bruju.rmeventreader.filereader.FileReaderByLine;

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

		try {
			FileReaderByLine.lireLeFichier(new File(CHEMIN_MOTIFS_CONNUS), line -> {
				if (line.startsWith("//")) {
					return;
				}
				
				Scanner scanner = new Scanner(line);

				String chaine = scanner.next();

				List<Integer> valeursMotif = new ArrayList<>();

				while (scanner.hasNextInt()) {
					valeursMotif.add(scanner.nextInt());
				}

				scanner.close();

				int[] valeurs = new int[valeursMotif.size()];
				for (int i = 0; i != valeursMotif.size(); i++) {
					valeurs[i] = valeursMotif.get(i);
				}

				motifs.add(new Motif(chaine, valeurs));
			});
		} catch (IOException e) {
			e.printStackTrace();
		}

		return motifs;
	}
}
