package fr.bruju.rmeventreader.implementation.detectiondeformules._variables;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;

import fr.bruju.rmeventreader.utilitaire.LecteurDeFichiersLigneParLigne;

public class EtatInitial {
	private static final String FICHIER_VARIABLES = "ressources/formulatracker/Injection.txt"; 
	private static EtatInitial instance = null;
	
	
	public static EtatInitial getEtatInitial() {
		if (instance == null) {
			instance = new EtatInitial();
		}
		
		return instance;
	}
	
	private Map<Integer, Integer> valeursAffectees;
	
	
	public EtatInitial() {
		valeursAffectees = new HashMap<>();
		LecteurDeFichiersLigneParLigne.lectureFichierRessources(FICHIER_VARIABLES, ligne -> {
			String[] donnees = ligne.split(" ");
			
			String idStr = donnees[0];
			Integer idInt = Integer.parseInt(idStr);
			String valeur = donnees[1];

			switch (valeur) {
				case "ON":
					valeursAffectees.put(-idInt, 1);
					break;
				case "OFF":
					valeursAffectees.put(-idInt, 0);
					break;
				default:
					valeursAffectees.put(idInt, Integer.parseInt(valeur));
					break;
			}
		});
	}
	
	public Integer getValeur(int idVariable) {
		return valeursAffectees.get(idVariable);
	}
	
	public Boolean getInterrupteur(int idInterrupteur) {
		Integer valeur = getValeur(-idInterrupteur);
		return valeur == null ? null : valeur == 1;
	}

	public void forEach(BiConsumer<Integer, Integer> fonction) {
		valeursAffectees.forEach(fonction);
	}
}
