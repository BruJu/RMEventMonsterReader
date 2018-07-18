package fr.bruju.rmeventreader.rmdatabase;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import fr.bruju.rmeventreader.utilitaire.Pair;

/**
 * Cette classe fourni une correspondance préfaite entre différentes valeurs et leurs noms dans une base de données
 * RPG Maker.
 * 
 * @author Bruju
 *
 */
public class BaseDeDonneesRPGMaker {
	/* =========
	 * Attributs
	 * ========= */
	
	/**
	 * Id Personnage - Nom
	 */
	private Map<Integer, String> nomDesPersonnages;
	
	/**
	 * Id Objet - Nom
	 */
	private Map<Integer, String> nomDesObjets;
	
	/**
	 * ID Variable - Nom
	 */
	private Map<Integer, String> nomDesVariables;
	
	/**
	 * ID Interrupteur - Nom
	 */
	private Map<Integer, String> nomDesInterrupteurs;
	
	/* ============
	 * Constructeur
	 * ============ */
	
	/**
	 * Instancie une base de données
	 */
	private BaseDeDonneesRPGMaker() {
		
	}
	
	/**
	 * Donne le nom du personnage portant l'id donné
	 */
	public String getNomDuPersonnage(Integer id) {
		return nomDesPersonnages.get(id);
	}

	/**
	 * Donne le nom de l'objet portant l'id donné
	 */
	public String getNomDeLObjet(Integer id) {
		return nomDesObjets.get(id);
	}

	/**
	 * Donne le nom de la variable portant l'id donné
	 */
	public String getNomDeLaVariable(Integer id) {
		return nomDesVariables.get(id);
	}

	/**
	 * Donne le nom de l'interrupteur portant l'id donné
	 */
	public String getNomDeLinterrupteur(Integer id) {
		return nomDesInterrupteurs.get(id);
	}

	/**
	 * Donne l'ID du personnage portant le nom donné
	 */
	Integer getIdPersonnage(String nom) {
		return search(nomDesPersonnages, nom);
	}

	/**
	 * Donne l'ID de l'objet portant le nom donné
	 */
	Integer getIdObjet(String objet) {
		return search(nomDesObjets, objet);
	}

	/**
	 * Donne l'ID de la variable portant le nom donné
	 */
	Integer getIdVariable(String variable) {
		return search(nomDesVariables, variable);
	}

	/**
	 * Donne l'ID de l'interrupteur portant le nom donné
	 */
	Integer getIdInterrupteur(String interrupteur) {
		return search(nomDesInterrupteurs, interrupteur);
	}
	
	/**
	 * Renvoie l'ID de l'objet dont le nom est valeurCherchée dans map
	 */
	private static Integer search(Map<Integer, String> map, String valeurCherchee) {
		for (Entry<Integer, String> entree : map.entrySet()) {
			if (entree.getValue().equals(valeurCherchee)) {
				return entree.getKey();
			}
		}
		
		return null;
	}
	
	/* =======
	 * Builder
	 * ======= */

	/**
	 * Monteur de base de données
	 * @author Bruju
	 *
	 */
	public static class Builder {
		/**
		 * Construit une base de données avec des noms de fichiers par défaut qui sont
		 * ressources/basededonnees/noms/{personnages|objets|variables|interrupteurs}.txt
		 * @return La base de données construite via les fichiers par défaut
		 */
		public static BaseDeDonneesRPGMaker parDefaut() {
			Builder build = new Builder();
			String cheminParDefaut = "ressources/basededonnees/noms/";
			
			try {
				build.setNomDesPersonnages  (cheminParDefaut + "personnages.txt"  );
				build.setNomDesObjets       (cheminParDefaut + "objets.txt"       );
				build.setNomDesVariables    (cheminParDefaut + "variables.txt"    );
				build.setNomDesInterrupteurs(cheminParDefaut + "interrupteurs.txt");
			} catch (IOException e) {
				e.printStackTrace();
				return null;
			}
			
			return build.construire();
		}
		
		/**
		 * Base de données construite
		 */
		private BaseDeDonneesRPGMaker bddConstruite;
		
		/**
		 * Crée un monteur de base de données vide
		 */
		public Builder() {
			bddConstruite = new BaseDeDonneesRPGMaker();
		}
		
		/**
		 * Renvoie la base de données construite
		 * @return La base de données
		 */
		public BaseDeDonneesRPGMaker construire() {
			return bddConstruite;
		}
		
		/**
		 * Construit la liste des personnages avec un fichier contenant des associations IDPersonnage - NomPersonnage
		 */
		public Builder setNomDesPersonnages(String fichier) throws IOException {
			List<Pair<String, String>> donnees = Utilitaire.lireFichierRessource(fichier);
			bddConstruite.nomDesPersonnages = Utilitaire.creerMap(donnees);
			return this;
		}

		/**
		 * Construit la liste des objets avec un fichier contenant des associations IDObjet - NomObjet
		 */
		public Builder setNomDesObjets(String fichier) throws IOException {
			List<Pair<String, String>> donnees = Utilitaire.lireFichierRessource(fichier);
			bddConstruite.nomDesObjets = Utilitaire.creerMap(donnees);
			return this;
		}

		/**
		 * Construit la liste des variables avec un fichier contenant des associations IDVariable - NomVariable
		 */
		public Builder setNomDesVariables(String fichier) throws IOException {
			List<Pair<String, String>> donnees = Utilitaire.lireFichierRessource(fichier);
			bddConstruite.nomDesVariables = Utilitaire.creerMap(donnees);
			return this;
		}

		/**
		 * Construit la liste des interrupteurs avec un fichier contenant des associations IDInterrupteur - Nom
		 */
		public Builder setNomDesInterrupteurs(String fichier) throws IOException {
			List<Pair<String, String>> donnees = Utilitaire.lireFichierRessource(fichier);
			bddConstruite.nomDesInterrupteurs = Utilitaire.creerMap(donnees);
			return this;
		}
	}

}
