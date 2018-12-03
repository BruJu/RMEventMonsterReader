package fr.bruju.rmeventreader.implementation.obtentionobjet;

import fr.bruju.rmdechiffreur.reference.Reference;
import fr.bruju.rmdechiffreur.reference.ReferenceEC;
import fr.bruju.rmdechiffreur.reference.ReferenceMap;
import fr.bruju.rmeventreader.implementation.chercheurdevariables.module.ObjetObtenu;
import fr.bruju.rmeventreader.implementation.magasin.ChercheurDeMagasins;
import fr.bruju.rmeventreader.implementation.magasin.Magasin;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

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
		int idObjet;

		try {
			idObjet = Integer.parseInt(saisie);
		} catch (NumberFormatException exception) {
			List<Integer> idTrouves = trouverTousLesObjetsContenant(saisie);

			if (idTrouves.isEmpty()) {
				System.out.println("Aucun objet ne contient " + saisie);
				return;
			} else if (idTrouves.size() > 1) {
				System.out.println("Plusieurs objets contiennent " + saisie);

				for (Integer objetTrouve : idTrouves) {
					System.out.println(objetTrouve + " : " + PROJET.extraireObjet(objetTrouve));
				}
				return;
			} else {
				idObjet = idTrouves.get(0);
			}
		}

		System.out.println("Objet : " + idObjet + " - " + PROJET.extraireObjet(idObjet));

		chercherAuSol(idObjet);
		chercherDansUnMagasin(idObjet);



		// TODO : Monstres

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

	public static List<Integer> trouverTousLesObjetsContenant(String nomPartieObjet) {
		List<Integer> idTrouves = new ArrayList<>();
		List<String> objetsExistants = PROJET.extraireObjets();

		System.out.println("Recherche " + nomPartieObjet);

		for (int i = 0 ; i != objetsExistants.size() ; i++) {
			if (objetsExistants.get(i).contains(nomPartieObjet)) {
				idTrouves.add(i+1);
			}
		}

		return idTrouves;
	}
}
