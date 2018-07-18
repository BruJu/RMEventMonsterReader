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
public class AffectationBaseDeDonnees {
	private BaseDeDonneesRPGMaker bddSupportee;

	private Map<Integer, Integer> objetsPossedes;
	private Map<Integer, List<Integer>> objetsEquipes;
	private Map<Integer, Integer> variablesAffectees;
	private Map<Integer, Boolean> interrupteursAffectes;

	private AffectationBaseDeDonnees(BaseDeDonneesRPGMaker base) {
		this.bddSupportee = base;
	}

	public Integer getQuantitePossedee(int idObjet) {
		return objetsPossedes.get(idObjet);
	}

	public Integer getVariable(int idVariable) {
		return variablesAffectees.get(idVariable);
	}

	public Boolean getInterrupteur(int idInterrupteur) {
		return interrupteursAffectes.get(idInterrupteur);
	}

	public Boolean herosPossedeEquipement(int idHeros, int idObjet) {
		List<Integer> objetsEquipesParLeHeros = objetsEquipes.get(idHeros);

		if (objetsEquipesParLeHeros == null) {
			return null;
		}

		return objetsEquipesParLeHeros.contains(idObjet);
	}

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

	public static class Builder {
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

		private AffectationBaseDeDonnees affectation;

		public Builder(BaseDeDonneesRPGMaker baseDeDonnees) {
			affectation = new AffectationBaseDeDonnees(baseDeDonnees);
		}

		public AffectationBaseDeDonnees construire() {
			return affectation;
		}

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
