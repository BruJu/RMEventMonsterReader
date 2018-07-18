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
	
	private Map<Integer, String> nomDesPersonnages;
	private Map<Integer, String> nomDesObjets;
	private Map<Integer, String> nomDesVariables;
	private Map<Integer, String> nomDesInterrupteurs;
	
	/* ============
	 * Constructeur
	 * ============ */
	
	private BaseDeDonneesRPGMaker() {
	}
	
	public String getNomDuPersonnage(Integer id) {
		return nomDesPersonnages.get(id);
	}

	public String getNomDeLObjet(Integer id) {
		return nomDesObjets.get(id);
	}
	
	public String getNomDeLaVariable(Integer id) {
		return nomDesVariables.get(id);
	}
	
	public String getNomDeLinterrupteur(Integer id) {
		return nomDesInterrupteurs.get(id);
	}


	Integer getIdPersonnage(String nom) {
		return search(nomDesObjets, nom);
	}

	Integer getIdObjet(String objet) {
		return search(nomDesObjets, objet);
	}

	Integer getIdVariable(String variable) {
		return search(nomDesObjets, variable);
	}

	Integer getIdInterrupteur(String interrupteur) {
		return search(nomDesObjets, interrupteur);
	}
	

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

	public static class Builder {
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
		
		
		private BaseDeDonneesRPGMaker bddConstruite;
		
		public Builder() {
			bddConstruite = new BaseDeDonneesRPGMaker();
		}
		
		public BaseDeDonneesRPGMaker construire() {
			return bddConstruite;
		}
		
		public Builder setNomDesPersonnages(String fichier) throws IOException {
			List<Pair<String, String>> donnees = Utilitaire.lireFichierRessource(fichier);
			bddConstruite.nomDesPersonnages = Utilitaire.creerMap(donnees);
			return this;
		}

		public Builder setNomDesObjets(String fichier) throws IOException {
			List<Pair<String, String>> donnees = Utilitaire.lireFichierRessource(fichier);
			bddConstruite.nomDesObjets = Utilitaire.creerMap(donnees);
			return this;
		}
		
		public Builder setNomDesVariables(String fichier) throws IOException {
			List<Pair<String, String>> donnees = Utilitaire.lireFichierRessource(fichier);
			bddConstruite.nomDesVariables = Utilitaire.creerMap(donnees);
			return this;
		}
		
		public Builder setNomDesInterrupteurs(String fichier) throws IOException {
			List<Pair<String, String>> donnees = Utilitaire.lireFichierRessource(fichier);
			bddConstruite.nomDesInterrupteurs = Utilitaire.creerMap(donnees);
			return this;
		}
	}

}
