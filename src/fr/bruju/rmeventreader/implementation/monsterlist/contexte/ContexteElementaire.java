package fr.bruju.rmeventreader.implementation.monsterlist.contexte;

import java.io.IOException;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.concurrent.atomic.AtomicBoolean;

import fr.bruju.rmeventreader.filereader.FileReaderByLine;

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

	/** Fichier ressource contenant les données à lire */
	public static final String FICHIER_RESSOURCE_CONTEXTE = "ressources\\monsterlist\\Resistances.txt";
	/** Prefixe des scripts de sous fonctions */
	public static final String RESSOURCES_PREFIXE = "ressources\\monsterlist\\Elements\\";
	/** Fichier contenant le script de départ des éléments */
	public static final String PREMIERFICHIER = "ressources/monsterlist/Elements/Premier.txt";
	/** Fichier contenant le sous script appelé lors du premier script */
	public static final String SECONDFICHIER = "ressources/monsterlist/Elements/Second.txt";
	
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
		
		try {
			FileReaderByLine.lireLeFichierSansCommentaires(chemin, ligne -> {
				if (ligne.equals("- Element -")) {
					etatActuel.set(true);
				} else if (ligne.equals("- Parties -")) {
					etatActuel.set(false);
				} else {
					String[] decomposition = ligne.split(" ");
					
					String nom = decomposition[1];
					Integer variable = Integer.decode(decomposition[0]);
					
					if (etatActuel.get()) {
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
