package fr.bruju.rmeventreader.implementation.recomposeur;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

import fr.bruju.rmeventreader.filereader.FileReaderByLine;

public class Parametres {
	private Map<String, List<String[]>> donneesLues;
	
	public Parametres(String chemin) {
		donneesLues = new HashMap<>();
		
		AtomicReference<String> sectionActuelle = new AtomicReference<>();
		
		String patternSection = "== _ ==";
		
		try {
			FileReaderByLine.lireLeFichierSansCommentaires(chemin, ligne -> {
				if (ligne.startsWith("== ") && ligne.endsWith(" ==")) {
					// Changer de section
					String nomSection = ligne.substring(3, ligne.length() - 3);
					
					donneesLues.putIfAbsent(nomSection, new ArrayList<>());
					sectionActuelle.set(nomSection);
					
				} else {
					String[] donnees = ligne.split(" ");
					
					donneesLues.get(sectionActuelle.get()).add(donnees);
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
