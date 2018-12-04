package fr.bruju.rmeventreader.implementation.recherchecombat;

import fr.bruju.rmdechiffreur.Projet;
import fr.bruju.rmeventreader.implementation.monsterlist.ListeurDeMonstres;
import fr.bruju.rmeventreader.implementation.monsterlist.metier.Combat;
import fr.bruju.rmeventreader.implementation.monsterlist.metier.MonsterDatabase;
import fr.bruju.rmeventreader.implementation.monsterlist.metier.Monstre;

import java.util.HashMap;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import static fr.bruju.rmeventreader.ProjetS.PROJET;

public class ListeurDeMonstresDansUneZone {


	public void run(String prefixeMap) {
		Set<Integer> idDeCombat = chercherIdDeCombats(prefixeMap);
		Set<MonstreSimplifie> monstres = extraireMonstres(idDeCombat);

		for (MonstreSimplifie monstre : monstres) {
			System.out.println(monstre.toString());
		}
	}

	private Set<MonstreSimplifie> extraireMonstres(Set<Integer> idDeCombat) {
		ListeurDeMonstres listeur = new ListeurDeMonstres(0);

		MonsterDatabase bdd = listeur.creerBaseDeDonnees();

		if (bdd == null) {
			// TODO : un meilleur traitement des erreurs
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
				monstres.get(monstreSimplifie).recevoir(monstreSimplifie);
			} else {
				monstres.put(monstreSimplifie, monstreSimplifie);
			}
		}
	}

	private Set<Integer> chercherIdDeCombats(String nomMapPartiel) {
		ChercheCombat recherche = new ChercheCombat();

		PROJET.explorerEvenements((map, evenement, page) -> {
			if (!map.nom().contains(nomMapPartiel)) {
				return;
			}

			recherche.viderRencontre();
			recherche.appliquerInstructions(page.instructions());
		});

		return recherche.getIdTrouves();
	}
}
