package fr.bruju.rmeventreader.implementation.recomposeur;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

import fr.bruju.rmeventreader.filereader.FileReaderByLine;
import fr.bruju.rmeventreader.utilitaire.Utilitaire;

public class Parametres {
	private Map<String, List<String[]>> donneesLues;
	
	public Parametres(String chemin) {
		donneesLues = new HashMap<>();
		
		AtomicReference<String> sectionActuelle = new AtomicReference<>();
		
		try {
			FileReaderByLine.lireLeFichierSansCommentaires(chemin, ligne -> {
				if (ligne.startsWith("== ") && ligne.endsWith(" ==")) {
					// Changer de section
					sectionActuelle.set(ligne.substring(3, ligne.length() - 3));
				} else {
					// Ajouter des données à la section courante
					Utilitaire.Maps.ajouterElementDansListe(donneesLues, sectionActuelle.get(), ligne.split(" "));
				}
			});
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public List<String[]> getParametres(String nomSection) {
		return donneesLues.get(nomSection);
	}
}
