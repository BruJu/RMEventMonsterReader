package fr.bruju.rmeventreader.implementation.recomposeur;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

import fr.bruju.rmeventreader.filereader.FileReaderByLine;
import fr.bruju.rmeventreader.filereader.Recognizer;

public class Parametres {
	private Map<String, List<String[]>> donneesLues;
	
	public Parametres(String chemin) {
		donneesLues = new HashMap<>();
		
		AtomicReference<String> sectionActuelle = new AtomicReference<>();
		
		String patternSection = "== _ ==";
		
		try {
			FileReaderByLine.lireLeFichierSansCommentaires(chemin, ligne -> {
				List<String> nouvelleSection = Recognizer.tryPattern(patternSection, ligne);
				
				if (nouvelleSection != null) {
					// Changer de section;
					String nomSection = nouvelleSection.get(0);
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
