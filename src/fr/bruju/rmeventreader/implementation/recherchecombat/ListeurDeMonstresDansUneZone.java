package fr.bruju.rmeventreader.implementation.recherchecombat;

import fr.bruju.rmeventreader.implementation.monsterlist.ListeurDeMonstres;
import fr.bruju.rmeventreader.implementation.monsterlist.metier.Combat;
import fr.bruju.rmeventreader.implementation.monsterlist.metier.MonsterDatabase;
import fr.bruju.rmeventreader.implementation.monsterlist.metier.Monstre;

import java.util.HashMap;
import java.util.Set;
import java.util.TreeSet;

import static fr.bruju.rmeventreader.ProjetS.PROJET;

/**
 * Une classe listant tous les combats ponctuels pouvant être trouvés dans des cartes dont l'arborescence contient une
 * chaîne.
 */
public class ListeurDeMonstresDansUneZone {
	/**
	 * Affiche tous les monstres dans une zone contenant dans son arborescence la chaîne donnée.
	 * @param chaine La chaîne
	 */
	public void afficherMonstresDansUneZone(String chaine) {
		Set<MonstreSimplifie> monstres = chercherMonstres(chaine);

		for (MonstreSimplifie monstre : monstres) {
			System.out.println(monstre.toString());
		}
	}

	/**
	 * Donne l'ensemble de tous les monstres pouvant être affrontés ponctuellement dans une zone dont l'aborescence
	 * contient la chaîne donnée
	 * @param chaine La chaîne devant être dans l'arborescence
	 * @return L'ensemble des monstres pouvant être affrontés lors de combats scriptés
	 */
	public Set<MonstreSimplifie> chercherMonstres(String chaine) {
		Set<Integer> idDeCombat = chercherIdDeCombats(chaine);
		Set<MonstreSimplifie> monstres = extraireMonstres(idDeCombat);
		return monstres;
	}

	// Recherche des ID de Combats

	/**
	 * Donne la liste de tous les numéros de combat pouvant être trouvés dans des map dont l'arborescence contient la
	 * chaîne.
	 * @param chaine La chaîne
	 * @return La liste des id de combats
	 */
	private Set<Integer> chercherIdDeCombats(String chaine) {
		ChercheCombat recherche = new ChercheCombat();

		PROJET.explorerEvenements((map, evenement, page) -> {
			if (!map.nom().contains(chaine)) {
				return;
			}

			recherche.viderRencontre();
			recherche.appliquerInstructions(page.instructions());
		});

		return recherche.getIdTrouves();
	}

	// Extraire les monstres des combats

	/**
	 * Converti l'ensemble des id de combat en l'ensemble des monstres qu'on y affronte
	 * @param idDeCombat La liste des combat
	 * @return La liste des monstres dans ces combats
	 */
	private Set<MonstreSimplifie> extraireMonstres(Set<Integer> idDeCombat) {
		MonsterDatabase bdd = ListeurDeMonstres.creerBaseDeDonnees();

		if (bdd == null) { // TODO : un meilleur traitement des erreurs
			return new TreeSet<>();
		}

		HashMap<MonstreSimplifie, MonstreSimplifie> monstres = new HashMap<>();

		for (Combat combat : bdd.extractBattles()) {
			if (!idDeCombat.contains(combat.id)) {
				continue;
			}

			completerMonstres(monstres, combat);
		}

		return monstres.keySet();
	}

	/**
	 * Ajoute dans la map de monstres les monstres contenus dans le combat donné
	 * @param monstres La map recevant les monstres présent dans les combats
	 * @param combat Le combat contenant les monstres à ajouter
	 */
	private static void completerMonstres(HashMap<MonstreSimplifie, MonstreSimplifie> monstres, Combat combat) {
		// En gros : c'est une implémentation de CollectorsBySimilarity sans flux
		// En plus détaillé :
		// equals et hashCode de MonstreSimplifie ne porte pas sur la partie "id de combats".
		// Donc deux monstres qui ont tout en commun sauf les combats où ils apparaissent ont le même hash et sont
		// indentiques au sens de equals.
		// Le but ici est de regrouper les monstres pour qu'un objet monstre simplifié ait tous les id de combats.
		// Si un monstre identique est déjà présent dans la map : on lui donne notre id de combat
		// Si il est absent, on prend cet emplacement (clé) et on se désigne nous même (valeur) pour recevoir tous les
		// id de combat.
		//
		// Cette astuce a été mise en place car si on a un objet o, on ne peut pas demander à un set de set.get(o).
		for (int i = 0 ; i != 3 ; i++) {
			Monstre monstre = combat.getMonstre(i);

			if (monstre == null) {
				continue;
			}

			MonstreSimplifie monstreSimplifie = new MonstreSimplifie(monstre);

			if (monstres.containsKey(monstreSimplifie)) {
				monstres.get(monstreSimplifie).copierIDDeCombats(monstreSimplifie);
			} else {
				monstres.put(monstreSimplifie, monstreSimplifie);
			}
		}
	}
}
