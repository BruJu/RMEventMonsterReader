package fr.bruju.rmeventreader.implementation.monsterlist.elements;

import java.io.IOException;
import java.util.Collection;
import java.util.LinkedHashMap;

import fr.bruju.rmeventreader.filereader.FileReaderByLine;
import fr.bruju.rmeventreader.utilitaire.Container;

public class ContexteElementaire {

	public static final String PARTIES = "PARTIES";
	public static final String ELEMENTS = "ELEMENTS";
	public static final String RESSOURCES_PREFIXE = "ressources\\monsterlist\\Elements\\";
	public static final String PREMIERFICHIER = "ressources/monsterlist/Elements/Premier.txt";
	public static final String SECONDFICHIER = "ressources/monsterlist/Elements/Second.txt";
	
	private LinkedHashMap<Integer, String> partiesConnues = new LinkedHashMap<>();
	private LinkedHashMap<Integer, String> elementsConnus = new LinkedHashMap<>();
	

	public String getPartie(int idVariable) {
		return partiesConnues.get(idVariable);
	}

	public String getElement(int idVariable) {
		return elementsConnus.get(idVariable);
	}

	public Collection<String> getParties() {
		return partiesConnues.values();
	}
	
	public Collection<String> getElements() {
		return elementsConnus.values();
	}
	
	public void lireContexteElementaire(String chemin) {
		Container<Boolean> etatActuel = new Container<>();
		etatActuel.item = true; // true = lecture d'élément ; false = lecture de parties
		
		try {
			FileReaderByLine.lireLeFichierSansCommentaires(chemin, ligne -> {
				if (ligne.equals("- Element -")) {
					etatActuel.item = true;
				} else if (ligne.equals("- Parties -")) {
					etatActuel.item = false;
				} else {
					String[] decomposition = ligne.split(" ");
					
					String nom = decomposition[1];
					Integer variable = Integer.decode(decomposition[0]);
					
					if (etatActuel.item) {
						elementsConnus.put(variable, nom);
					} else {
						partiesConnues.put(variable, nom);
					}
				}
			});
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
