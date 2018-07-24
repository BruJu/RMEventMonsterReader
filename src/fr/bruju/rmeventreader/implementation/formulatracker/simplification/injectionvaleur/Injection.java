package fr.bruju.rmeventreader.implementation.formulatracker.simplification.injectionvaleur;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fr.bruju.rmeventreader.rmdatabase.Utilitaire;
import fr.bruju.rmeventreader.utilitaire.Pair;

public class Injection {
	private Map<Integer, Boolean> interrupteurs;
	private Map<Integer, Integer> variables;
	
	public Injection() {
		interrupteurs = new HashMap<>();
		variables = new HashMap<>();
	}
	
	public void remplirAvecFichier(String chemin) {
		List<Pair<String, String>> ressources = Utilitaire.lireFichierRessource(chemin);
		
		ressources.forEach(paire -> {
			
			String idStr = paire.getLeft();
			Integer idInt = Integer.parseInt(idStr);
			String valeur = paire.getRight();
			
			if (valeur.equals("ON")) {
				interrupteurs.put(idInt, true);
			} else if (valeur.equals("OFF")) {
				interrupteurs.put(idInt, false);
			} else {
				variables.put(idInt, Integer.parseInt(valeur));
			}
		});
	}

	public Boolean getInterrupteur(int numero) {
		return interrupteurs.get(numero);
	}

	public Integer getVariable(int numero) {
		return variables.get(numero);
	}
}
