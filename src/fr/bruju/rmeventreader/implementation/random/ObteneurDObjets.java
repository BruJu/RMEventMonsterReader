package fr.bruju.rmeventreader.implementation.random;

import fr.bruju.rmdechiffreur.reference.Reference;
import fr.bruju.rmdechiffreur.reference.ReferenceEC;
import fr.bruju.rmdechiffreur.reference.ReferenceMap;
import fr.bruju.rmeventreader.implementation.chercheurdevariables.module.ObjetObtenu;
import fr.bruju.rmeventreader.implementation.magasin.ChercheurDeMagasins;
import fr.bruju.rmeventreader.implementation.magasin.Magasin;
import fr.bruju.rmeventreader.implementation.monsterlist.ListeurDeMonstres;
import fr.bruju.rmeventreader.implementation.monsterlist.metier.MonsterDatabase;
import fr.bruju.rmeventreader.implementation.monsterlist.metier.Monstre;
import fr.bruju.rmeventreader.interfaceutilisateur.RechercheDansDictionnaire;
import fr.bruju.util.Pair;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static fr.bruju.rmeventreader.ProjetS.PROJET;

/**
 * Ce module a pour but de permettre la recherche d'objets
 */
public class ObteneurDObjets implements Runnable {
	private final String saisie;

	public ObteneurDObjets(String saisie) {
		this.saisie = saisie;
	}


	@Override
	public void run() {
		Pair<Integer, String> objet = RechercheDansDictionnaire.objetUnique(saisie);

		if (objet == null) {
			return;
		}

		System.out.println("Objet : " + objet.getRight());

		int idObjet = objet.getLeft();

		chercherAuSol(idObjet);
		chercherDansUnMagasin(idObjet);
		chercherUnObjetSurLesMonstres(objet.getRight());
	}

	private void chercherUnObjetSurLesMonstres(String objet) {
		MonsterDatabase bdd = new ListeurDeMonstres(0).creerBaseDeDonnees();

		if (bdd == null) {
			return;
		}

		List<String> monstres = bdd.extractMonsters()
				.stream()
				.filter(monstre -> monstre.nomDrop.equals(objet))
				.flatMap(monstre -> monstre.combat.fonds.stream().map(fond -> fond + " - " + monstre.nom))
				.sorted()
				.distinct()
				.collect(Collectors.toList());

		if (!monstres.isEmpty()) {
			System.out.println("• Drop");

			for (String monstre : monstres) {
				System.out.println(monstre);
			}
		}
	}

	private void chercherDansUnMagasin(int idObjet) {
		ChercheurDeMagasins chercheur = new ChercheurDeMagasins();
		chercheur.chercher();
		List<Magasin> magasins = chercheur.obtenirTousLesMagasinsPossedant(idObjet);

		if (!magasins.isEmpty()) {
			System.out.println("• En vente");
			for (Magasin magasin : magasins) {
				System.out.println(magasin.getLieu());
			}
		}
	}

	public void chercherAuSol(int idObjet) {
		ObjetObtenu rechercheSurCarte = new ObjetObtenu(idObjet);
		rechercheSurCarte.chercher();
		Set<Reference> donnes = rechercheSurCarte.getReferences();

		if (!donnes.isEmpty()) {
			System.out.println("• Donné");

			for (Reference reference : donnes) {
				if (reference instanceof ReferenceEC) {
					ReferenceEC ec = (ReferenceEC) reference;
					System.out.println("Evenement commun : " + ec.nom);
				} else {
					ReferenceMap map = (ReferenceMap) reference;
					System.out.println(map.nomMap + " : " + map.nomEvent);
				}
			}
		}
	}
}
