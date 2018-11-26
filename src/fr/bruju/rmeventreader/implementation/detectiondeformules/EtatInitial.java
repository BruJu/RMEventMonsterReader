package fr.bruju.rmeventreader.implementation.detectiondeformules;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;

import fr.bruju.rmeventreader.utilitaire.LecteurDeFichiersLigneParLigne;

/**
 * Permet de définir des valeurs initiales contenues dans un fichier à certaines variables.
 */
public class EtatInitial {
	/** Fichier ressource contenant la liste des valeurs initiales des variables */
	public static final String FICHIER_VARIABLES = "ressources/formulatracker/Injection.txt";

	/* =========
	 * SINGLETON
	 * ========= */

	private static EtatInitial instance = null;
	
	public static EtatInitial getEtatInitial() {
		if (instance == null) {
			instance = new EtatInitial();
		}
		
		return instance;
	}

	/* ============
	 * ETAT INITIAL
	 * ============ */

	private Map<Integer, Integer> valeursAffectees;
	
	private EtatInitial() {
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

	/**
	 * Donne la valeur de la variable idVariable
	 * @param idVariable Le numéro de la variable
	 * @return La valeur initiale de cette variable, ou null si elle n'en a pas
	 */
	public Integer getValeur(int idVariable) {
		return valeursAffectees.get(idVariable);
	}

	/**
	 * Donne la valeur de l'interrupteur idInterrupteur
	 * @param idInterrupteur Le numéro de l'interrupteur
	 * @return L'état de l'interrupteur, ou null si il n'en a pas
	 */
	public Boolean getInterrupteur(int idInterrupteur) {
		Integer valeur = getValeur(-idInterrupteur);
		return valeur == null ? null : valeur == 1;
	}

	/**
	 * Effectue une action pour chaque variable et interrupteur (les interrupteurs sont des variables étiquetés
	 * négativement contenant 1 si vrai, 0 si faux)
	 * @param fonction La fonction à exécuter pour chaque couple (id variable, valeur initiale)
	 */
	public void forEach(BiConsumer<Integer, Integer> fonction) {
		valeursAffectees.forEach(fonction);
	}
}
