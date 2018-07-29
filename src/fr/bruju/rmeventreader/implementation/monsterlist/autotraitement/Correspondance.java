package fr.bruju.rmeventreader.implementation.monsterlist.autotraitement;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fr.bruju.rmeventreader.filereader.FileReaderByLine;
import fr.bruju.rmeventreader.filereader.Recognizer;
import fr.bruju.rmeventreader.implementation.monsterlist.metier.MonsterDatabase;
import fr.bruju.rmeventreader.implementation.monsterlist.metier.Monstre;

/**
 * Action consistant à faire remplacer des valeurs par d'autres à travers un dictionnaire.
 * 
 * @author Bruju
 *
 */
public class Correspondance implements Runnable {
	/**
	 * Base de données
	 */
	private MonsterDatabase database;
	
	/**
	 * Fonctions de remplacement
	 */
	private Remplacement remplaceur;
	
	/**
	 * Nom du fichier source
	 */
	private String filename;
	
	/**
	 * Construit une action dont le but sera de remplacer des données dans la base de données en fonction du
	 * remplaceur et avec les données fournies par le fichier filename
	 * @param database La base de données
	 * @param remplaceur Les fonctions de remplacement
	 * @param filename Le fichier ressources qui contient des lignes de la forme valeurARemplacer valeurRemplacée
	 */
	public Correspondance(MonsterDatabase database, Remplacement remplaceur, String filename) {
		this.database = database;
		this.remplaceur = remplaceur;
		this.filename = filename;
	}
	
	@Override
	public void run() {
		try {
			lireFichier(filename);
			searchAndReplace(database.extractMonsters());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	
	/** Correspondance chaîne trouvée - chaîne à remplacer */
	private Map<String, String> map = new HashMap<>();

	/**
	 * Lit un fichier pour remplir les correspondances
	 */
	public void lireFichier(String chemin) throws IOException {
		String pattern = "_ _";
		
		FileReaderByLine.lireLeFichierSansCommentaires(chemin, line -> {
			List<String> chaines = Recognizer.tryPattern(pattern, line);

			map.put(chaines.get(0), chaines.get(1));
		});
	}

	/**
	 * Remplace les chaînes des monstres en appliquant les fonctions de recherche et de transformation données
	 * @param monstres
	 * @param search
	 * @param replace
	 */
	public void searchAndReplace(Collection<Monstre> monstres) {
		monstres.forEach(monstre -> {
			String id = remplaceur.getSearch(monstre);

			String valeur = map.get(id);

			if (valeur != null) {
				remplaceur.getReplace(monstre, valeur);
			}
		});
	}
	
	/**
	 * Classe permettant de remplacer des données dans les monstres
	 */
	public static interface Remplacement {
		/** Fonction de recherche de valeur dans un monstre */
		public String getSearch(Monstre monstre);

		/** Fonction d'application de la chaîne au monstre */
		public void getReplace(Monstre monstre, String nouveauNom);
		
		/**
		 * Chercheur et remplaceur de nom
		 */
		public static Remplacement nom() {
			return new Remplacement() {
				@Override
				public String getSearch(Monstre monstre) {
					return monstre.nom;
				}

				@Override
				public void getReplace(Monstre monstre, String nouveauNom) {
					monstre.nom = nouveauNom;
				}
			};
		}
		
		/**
		 * Chercheur et remplaceur de drop
		 */
		public static Remplacement drop() {
			return new Remplacement() {
				@Override
				public String getSearch(Monstre monstre) {
					return monstre.nomDrop;
				}

				@Override
				public void getReplace(Monstre monstre, String nouveauNom) {
					monstre.nomDrop = nouveauNom;
				}
			};
		}
	}

}
