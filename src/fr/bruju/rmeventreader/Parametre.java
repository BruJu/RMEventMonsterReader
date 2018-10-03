package fr.bruju.rmeventreader;

import java.util.HashMap;
import java.util.Map;

import fr.bruju.rmeventreader.utilitaire.LecteurDeFichiersLigneParLigne;

/**
 * Lit des paramètres dans le fichier ressources/Parametres.txt 
 * 
 * @author Bruju
 *
 */
public class Parametre {
	/** Fichier ressource */
	private static final String FICHIER = "ressources\\Parametres.txt";
	
	/** Association entre nom de paramètre et valeur */
	private Map<String, String> parametres;
	
	/**
	 * Rempli la liste des paramètres avec le fichier ressource
	 */
	private void remplir() {
		parametres = new HashMap<>();
		
		LecteurDeFichiersLigneParLigne.lectureFichierRessources(FICHIER, ligne -> {
			String[] paire = ligne.split(" ", 2);
			parametres.put(paire[0], paire[1]);
		});
	}
	
	/**
	 * Donne la valeur du paramètre demandé
	 * @param nomParametre Le nom du paramètre
	 * @return La valeur
	 */
	public String lire(String nomParametre) {
		return parametres.get(nomParametre);
	}
	
	
	/* =========
	 * SINGLETON
	 * ========= */
	
	/** Instance */
	private static Parametre instance;

	/**
	 * Construit une instance de paramètres
	 */
	private Parametre() {
		remplir();
	}

	/**
	 * Donne l'instance de l'objet
	 * @return L'instance
	 */
	public static Parametre getInstance() {
		if (null == instance) {
			instance = new Parametre();
		}
		return instance;
	}
	
	/**
	 * Donne le paramètre dont le nom est donné
	 * @param nomParametre Le nom du paramètre
	 * @return
	 */
	public static String get(String nomParametre) {
		return getInstance().parametres.get(nomParametre);
	}
}
