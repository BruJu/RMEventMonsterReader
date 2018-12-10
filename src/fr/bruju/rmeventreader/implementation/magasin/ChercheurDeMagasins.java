package fr.bruju.rmeventreader.implementation.magasin;

import java.util.*;
import java.util.stream.Collectors;

import fr.bruju.lcfreader.rmobjets.RMEvenement;
import fr.bruju.lcfreader.rmobjets.RMMap;
import fr.bruju.lcfreader.rmobjets.RMPage;
import fr.bruju.rmdechiffreur.reference.ReferenceMap;

import static fr.bruju.rmeventreader.ProjetS.PROJET;

public class ChercheurDeMagasins implements Runnable {
	private Map<Integer, Magasin> magasins;

	public ChercheurDeMagasins() {
	}

	/**
	 *
	 * Pas dans le constructeur pour que l'instanciation de l'objet ne lance pas la recherche
	 * @return
	 */
	public ChercheurDeMagasins chercher() {
		magasins = new HashMap<>();
		PROJET.explorerEvenements(this::chercherMagasin);
		PROJET.lireEvenement(new RemplisseurDeNiveaux(magasins), 461, 88, 1);
		PROJET.lireEvenement(new RemplisseurDObjets(magasins), 461, 5, 1);
		return this;
	}

	public List<Magasin> obtenirTousLesMagasinsPossedant(int idObjet) {
		List<Magasin> magasinsFiltres = new ArrayList<>();

		for (Magasin magasin : magasins.values()) {
			if (magasin.possedeLObjetdID(idObjet)) {
				magasinsFiltres.add(magasin);
			}
		}

		return magasinsFiltres;
	}

	private void chercherMagasin(RMMap map, RMEvenement event, RMPage page) {
		PROJET.executer(new ChercheurDeMagasinDansPage(map, new ReferenceMap(map, event, page), magasins),
				page.instructions());
	}

	@Override
	public void run() {
		chercher();
		StringJoiner sj = new StringJoiner("\n");

		for (Magasin magasin : magasins.values()) {
			sj.add(magasin.getMagasinCompact());
		}

		System.out.println(sj.toString());

		afficherMagasinsComplets(magasins.values());
	}

	public static void afficherMagasinsComplets(Collection<Magasin> magasins) {
		for (Magasin magasin : magasins) {
			System.out.println();
			System.out.print(magasin.getMagasinComplet());
		}
	}

	public Collection<Magasin> getMagasins() {
		return magasins.values();
	}
}
