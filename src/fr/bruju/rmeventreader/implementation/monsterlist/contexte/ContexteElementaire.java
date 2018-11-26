package fr.bruju.rmeventreader.implementation.monsterlist.contexte;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.concurrent.atomic.AtomicBoolean;

import fr.bruju.rmeventreader.utilitaire.LecteurDeFichiersLigneParLigne;

/** 
 * Lit des fichiers du type
 * <pre>
 * - Element -
 * IdVariable NomElement
 * 
 * - Parties -
 * IdInterrupteur NomPartie
 * 
 * </pre>
 * pour connaître la correspondance variable - élément du monstre.
 * 
 */
public class ContexteElementaire {
	/* ==========
	 * CONSTANTES
	 * ========== */
	
	// Fichiers ressources

	
	// Données dans les monstres
	
	/** Ensemble de données contenant les parties dans les monstres */
	public static final String PARTIES = "PARTIES";
	/** Ensemble de données contenant les éléments dans les monstres */
	public static final String ELEMENTS = "ELEMENTS";
	
	/* =======================
	 * CONTEXTE ELEMENTAIRE LU
	 * ======================= */
	
	/** Associations interrupteur - nom de la partie */
	private LinkedHashMap<Integer, String> partiesConnues = new LinkedHashMap<>();
	/** Associations variable - élément */
	private LinkedHashMap<Integer, String> elementsConnus = new LinkedHashMap<>();

	/** Donne le nom de la partie concernée par le switch dont le numéro est donné */
	public String getPartie(int idVariable) {
		return partiesConnues.get(idVariable);
	}

	/** Donne le nom de l'élément concerné par la variable dont le numéro est donné */
	public String getElement(int idVariable) {
		return elementsConnus.get(idVariable);
	}

	/** Donne la liste de toutes les parties connues */
	public Collection<String> getParties() {
		return partiesConnues.values();
	}
	
	/** Donne la liste de tous les éléments connus */
	public Collection<String> getElements() {
		return elementsConnus.values();
	}

	/* =======================
	 * CONTEXTE ELEMENTAIRE LU
	 * ======================= */
	
	/**
	 * Rempli ce contexte élémentaire avec le fichier donné
	 * @param chemin Le nom du fichier contenant les ressources
	 */
	public void lireContexteElementaire(String chemin) {
		AtomicBoolean etatActuel = new AtomicBoolean(true); // true = lecture d'élément ; false = lecture de parties
		
		LecteurDeFichiersLigneParLigne.lectureFichierRessources(chemin, ligne -> {
			switch (ligne) {
				case "- Element -":
					etatActuel.set(true);
					break;
				case "- Parties -":
					etatActuel.set(false);
					break;
				default:
					String[] decomposition = ligne.split(" ");

					String nom = decomposition[1];
					Integer variable = Integer.decode(decomposition[0]);

					if (etatActuel.get()) {
						elementsConnus.put(variable, nom);
					} else {
						partiesConnues.put(variable, nom);
					}
					break;
			}
		});
	}
	
}
