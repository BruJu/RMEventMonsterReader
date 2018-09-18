package fr.bruju.rmeventreader.implementation.magasin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import fr.bruju.rmeventreader.actionmakers.executeur.controlleur.Explorateur;
import fr.bruju.rmeventreader.dictionnaires.liblcfreader.FabriqueCache;
import fr.bruju.rmeventreader.implementation.chercheurdevariables.reference.ReferenceMap;
import fr.bruju.lcfreader.rmobjets.RMEvenement;
import fr.bruju.lcfreader.rmobjets.RMInstruction;
import fr.bruju.lcfreader.rmobjets.RMMap;
import fr.bruju.lcfreader.rmobjets.RMPage;

public class ChercheurDeMagasins implements Runnable {
	private Map<Integer, Magasin> magasins;

	public Map<Integer, Magasin> chercher() {
		magasins = new HashMap<>();

		Explorateur.explorer(null, this::chercherMagasin);

		List<RMInstruction> niveaux = FabriqueCache.getInstance().page(461, 88, 1).instructions();
		Explorateur.executer(new RemplisseurDeNiveaux(magasins), niveaux);

		List<RMInstruction> objets = FabriqueCache.getInstance().page(461, 5, 1).instructions();
		Explorateur.executer(new RemplisseurDObjets(magasins), objets);

		return magasins;
	}

	public void chercherMagasin(RMMap map, RMEvenement event, RMPage page) {
		Explorateur.executer(new ChercheurDeMagasinDansPage(map, new ReferenceMap(map, event, page), magasins),
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
