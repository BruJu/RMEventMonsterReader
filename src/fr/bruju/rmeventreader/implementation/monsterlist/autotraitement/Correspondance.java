package fr.bruju.rmeventreader.implementation.monsterlist.autotraitement;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import fr.bruju.rmeventreader.filereader.FileReaderByLine;
import fr.bruju.rmeventreader.filereader.Recognizer;
import fr.bruju.rmeventreader.implementation.monsterlist.metier.Combat;
import fr.bruju.rmeventreader.implementation.monsterlist.metier.MonsterDatabase;
import fr.bruju.rmeventreader.implementation.monsterlist.metier.Monstre;

/**
 * Action consistant à faire remplacer des valeurs par d'autres à travers un dictionnaire.
 * 
 * @author Bruju
 *
 */
public class Correspondance<T> implements Runnable {
	/**
	 * Base de données
	 */
	private MonsterDatabase database;
	
	/**
	 * Fonctions de remplacement
	 */
	private Remplacement<T> remplaceur;
	
	/**
	 * Nom du fichier source
	 */
	private String filename;

	/** Fonction d'extraction */
	private Function<MonsterDatabase, Collection<T>> fonctionDextraction;
	
	/**
	 * Construit une action dont le but sera de remplacer des données dans la base de données en fonction du
	 * remplaceur et avec les données fournies par le fichier filename
	 * @param database La base de données
	 * @param remplaceur Les fonctions de remplacement
	 * @param filename Le fichier ressources qui contient des lignes de la forme valeurARemplacer valeurRemplacée
	 */
	public Correspondance(MonsterDatabase database, Remplacement<T> remplaceur, String filename, Function<MonsterDatabase, Collection<T>> fonctionDextraction) {
		this.database = database;
		this.remplaceur = remplaceur;
		this.filename = filename;
		this.fonctionDextraction = fonctionDextraction;
	}
	
	@Override
	public void run() {
		try {
			lireFichier(filename);
			searchAndReplace(fonctionDextraction.apply(database));
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
	public void searchAndReplace(Collection<T> monstres) {
		monstres.forEach(m -> remplaceur.remplacer(m, map));
	}
	
	/**
	 * Classe permettant de remplacer des données dans les monstres
	 */
	public static interface Remplacement<T> {
		public void remplacer(T monstre, Map<String, String> correspondances);

		
		/**
		 * Chercheur et remplaceur de nom
		 */
		public static Remplacement<Monstre> nom() {
			return new Remplacement<Monstre>() {
				@Override
				public void remplacer(Monstre monstre, Map<String, String> correspondances) {
					String nomPresent = monstre.nom;
					String remplacement = correspondances.get(nomPresent);
					
					if (remplacement != null) {
						monstre.nom = remplacement;
					}
				}
			};
		}
		
		/**
		 * Chercheur et remplaceur de drop
		 */
		public static Remplacement<Monstre> drop() {
			return new Remplacement<Monstre>() {
				@Override
				public void remplacer(Monstre monstre, Map<String, String> correspondances) {
					String nomPresent = monstre.nomDrop;
					String remplacement = correspondances.get(nomPresent);
					
					if (remplacement != null) {
						monstre.nomDrop = remplacement;
					}
				}
			};
		}
		
		
		/**
		 * Chercheur et remplaceur de zone
		 */
		public static Remplacement<Combat> fond() {
			return new Remplacement<Combat>() {
				@Override
				public void remplacer(Combat combat, Map<String, String> correspondances) {
					for (int i = 0 ; i != combat.fonds.size() ; i++) {
						String nomPresent = combat.fonds.get(i);
						String remplacement = correspondances.get(nomPresent);
						
						if (remplacement != null) {
							if (combat.fonds.contains(remplacement)) {
								combat.fonds.remove(i);
								i--;
								continue;
							}
							
							combat.fonds.set(i, remplacement);
						}
					}
				}
			};
		}
	}

}
