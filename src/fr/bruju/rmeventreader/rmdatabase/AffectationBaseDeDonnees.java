package fr.bruju.rmeventreader.rmdatabase;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fr.bruju.rmeventreader.utilitaire.Pair;

/**
 * Affectation d'une base de données.
 * 
 * @author Bruju
 *
 */
public class AffectationBaseDeDonnees implements Affectation {
	/* =========
	 * Attributs
	 * ========= */
	
	/**
	 * Base de données sur laquelle l'affectation est faite
	 */
	private BaseDeDonneesRPGMaker bddSupportee;

	/**
	 * Map associant idobjet -> quantité possédée
	 */
	private Map<Integer, Integer> objetsPossedes;
	
	/**
	 * Map associant idpersonnage -> liste des objets dont il est équipé
	 */
	private Map<Integer, List<Integer>> objetsEquipes;
	
	/**
	 * Affectation variable - valeur
	 */
	private Map<Integer, Integer> variablesAffectees;
	
	/**
	 * Affectation interrupteur - état
	 */
	private Map<Integer, Boolean> interrupteursAffectes;
	
	/**
	 * Construit une affectation de base de données ayant pour support la base donnée
	 * @param base La base de données contenant les noms des valeurs
	 */
	private AffectationBaseDeDonnees(BaseDeDonneesRPGMaker base) {
		this.bddSupportee = base;
	}

	/* =======
	 * Getters
	 * ======= */
	
	/**
	 * Renvoie la base de données supportée
	 * @return La base de données
	 */
	public BaseDeDonneesRPGMaker get() {
		return this.bddSupportee;
	}
	
	/* (non-Javadoc)
	 * @see fr.bruju.rmeventreader.rmdatabase.Affectation#getQuantitePossedee(int)
	 */
	@Override
	public Integer getQuantitePossedee(int idObjet) {
		return objetsPossedes.get(idObjet);
	}

	/* (non-Javadoc)
	 * @see fr.bruju.rmeventreader.rmdatabase.Affectation#getVariable(int)
	 */
	@Override
	public Integer getVariable(int idVariable) {
		return variablesAffectees.get(idVariable);
	}

	/* (non-Javadoc)
	 * @see fr.bruju.rmeventreader.rmdatabase.Affectation#getInterrupteur(int)
	 */
	@Override
	public Boolean getInterrupteur(int idInterrupteur) {
		return interrupteursAffectes.get(idInterrupteur);
	}
	
	/* (non-Javadoc)
	 * @see fr.bruju.rmeventreader.rmdatabase.Affectation#herosPossedeEquipement(int, int)
	 */
	@Override
	public Boolean herosPossedeEquipement(int idHeros, int idObjet) {
		List<Integer> objetsEquipesParLeHeros = objetsEquipes.get(idHeros);

		if (objetsEquipesParLeHeros == null) {
			return null;
		}

		return objetsEquipesParLeHeros.contains(idObjet);
	}

	/**
	 * Affiche sur la console l'affectation
	 */
	public void display() {
		objetsPossedes.forEach((item, quantite) -> System.out.println("item " + item + "has " + quantite));
		variablesAffectees.forEach((variable, valeur) -> System.out.println("Variable " + variable + " = " + valeur));
		interrupteursAffectes
				.forEach((interrupteur, valeur) -> System.out.println("Switch " + interrupteur + " = " + valeur));
		objetsEquipes.forEach((perso, items) -> {
			System.out.print("perso " + perso + "-> ");
			items.forEach(item -> System.out.print(item + " "));
			System.out.println();
		});

	}

	/* =======
	 * Builder
	 * ======= */

	/**
	 * Monteur d'afffectations
	 * @author Bruju
	 *
	 */
	public static class Builder {
		/**
		 * Construit une affectation avec les fichiers
		 * ressources/basededonnees/etat/{equipement|objetspossedes|variables|interrupteurs}.txt}
		 * @param baseDeDonnees La base de données support
		 * @return Une affectation remplie avec des noms de fichiers par défaut
		 */
		public static AffectationBaseDeDonnees parDefaut(BaseDeDonneesRPGMaker baseDeDonnees) {
			Builder build = new Builder(baseDeDonnees);
			String cheminParDefaut = "ressources/basededonnees/etat/";

			try {
				build.setObjetsEquipes(cheminParDefaut + "equipement.txt");
				build.setObjetsPossedes(cheminParDefaut + "objetspossedes.txt");
				build.setVariablesAffectees(cheminParDefaut + "variables.txt");
				build.setInterrupteursAffectes(cheminParDefaut + "interrupteurs.txt");

			} catch (IOException e) {
				e.printStackTrace();
				return null;
			}

			return build.construire();
		}
		
		/**
		 * Affectation en cours de construction
		 */
		private AffectationBaseDeDonnees affectation;

		/**
		 * Construit un monteur pour une affectation de la base de données
		 * @param baseDeDonnees La base de de données qui sera affectée 
		 */
		public Builder(BaseDeDonneesRPGMaker baseDeDonnees) {
			affectation = new AffectationBaseDeDonnees(baseDeDonnees);
		}

		public AffectationBaseDeDonnees construire() {
			return affectation;
		}

		/**
		 * Modifie les objets équipés avec un fichier contenant des associations quantité - nomObjet
		 */
		public Builder setObjetsPossedes(String fichier) throws IOException {
			List<Pair<String, String>> donnees = Utilitaire.lireFichierRessource(fichier);

			Map<Integer, Integer> map = new HashMap<>();

			donnees.forEach(paire -> {
				String objet = paire.getRight();
				Integer idObjet = affectation.bddSupportee.getIdObjet(objet);
				Integer quantite = Integer.decode(paire.getLeft());

				map.put(idObjet, quantite);
			});

			affectation.objetsPossedes = map;

			return this;
		}

		/**
		 * Modifie les objets équipés avec un fichier contenant des associations idVariable - valeur
		 */
		public Builder setVariablesAffectees(String fichier) throws IOException {
			List<Pair<String, String>> donnees = Utilitaire.lireFichierRessource(fichier);

			Map<Integer, Integer> map = new HashMap<>();

			donnees.forEach(paire -> {
				Integer idVariable = Integer.decode(paire.getLeft());
				Integer valeur = Integer.decode(paire.getRight());

				map.put(idVariable, valeur);
			});

			affectation.variablesAffectees = map;

			return this;
		}

		/**
		 * Modifie les objets équipés avec un fichier contenant des associations idInterrupteur - ON|OFF
		 */
		public Builder setInterrupteursAffectes(String fichier) throws IOException {
			List<Pair<String, String>> donnees = Utilitaire.lireFichierRessource(fichier);

			Map<Integer, Boolean> map = new HashMap<>();

			donnees.forEach(paire -> {
				Integer idInterrupteur = Integer.decode(paire.getLeft());

				map.put(idInterrupteur, paire.getRight().equals("ON"));
			});

			affectation.interrupteursAffectes = map;

			return this;
		}
		
		/**
		 * Modifie les objets équipés avec un fichier contenant des associations nomPerso - nomObjet
		 */
		public Builder setObjetsEquipes(String fichier) throws IOException {
			List<Pair<String, String>> donnees = Utilitaire.lireFichierRessource(fichier);

			Map<Integer, List<Integer>> map = new HashMap<>();

			donnees.forEach(paire -> {
				Integer idPerso = affectation.bddSupportee.getIdPersonnage(paire.getLeft());
				Integer idObjet = affectation.bddSupportee.getIdObjet(paire.getRight());

				List<Integer> listeObjetsEquipes = map.get(idPerso);

				if (listeObjetsEquipes == null) {
					listeObjetsEquipes = new ArrayList<>();
					listeObjetsEquipes.add(idObjet);
					map.put(idPerso, listeObjetsEquipes);
				} else {
					listeObjetsEquipes.add(idObjet);
				}
			});

			affectation.objetsEquipes = map;

			return this;
		}

	}
}
