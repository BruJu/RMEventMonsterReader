package fr.bruju.rmeventreader.implementation.magasin;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import fr.bruju.lcfreader.rmobjets.RMEvenement;
import fr.bruju.lcfreader.rmobjets.RMMap;
import fr.bruju.lcfreader.rmobjets.RMPage;
import fr.bruju.rmdechiffreur.reference.ReferenceMap;

import static fr.bruju.rmeventreader.ProjetS.PROJET;

public class ChercheurDeMagasins implements Runnable {
	private Map<Integer, Magasin> magasins;

	public Map<Integer, Magasin> chercher() {
		magasins = new HashMap<>();
		PROJET.explorerEvenements(this::chercherMagasin);
		PROJET.lireEvenement(new RemplisseurDeNiveaux(magasins), 461, 88, 1);
		PROJET.lireEvenement(new RemplisseurDObjets(magasins), 461, 5, 1);
		return magasins;
	}

	private void chercherMagasin(RMMap map, RMEvenement event, RMPage page) {
		PROJET.executer(new ChercheurDeMagasinDansPage(map, new ReferenceMap(map, event, page), magasins),
				page.instructions());
	}

	@Override
	public void run() {
		chercher();

		System.out.println(
				magasins.values().stream().map(mag -> mag.getMagasinCompact()).collect(Collectors.joining("\n")));

		magasins.values().forEach(magasin -> {
			System.out.println();
			System.out.print(magasin.getMagasinComplet());
		});
	}
}
