package fr.bruju.rmeventreader.implementation.magasin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import fr.bruju.rmeventreader.actionmakers.executeur.controlleur.Explorateur;
import fr.bruju.rmeventreader.dictionnaires.liblcfreader.LecteurDeCache;
import fr.bruju.rmeventreader.dictionnaires.modele.Evenement;
import fr.bruju.rmeventreader.dictionnaires.modele.Instruction;
import fr.bruju.rmeventreader.dictionnaires.modele.MapGeneral;
import fr.bruju.rmeventreader.dictionnaires.modele.Page;
import fr.bruju.rmeventreader.implementation.chercheurdevariables.reference.ReferenceMap;

public class ChercheurDeMagasins implements Runnable {
	private Map<Integer, Magasin> magasins;
	
	
	public Map<Integer, Magasin> chercher() {
		magasins = new HashMap<>();
		
		Explorateur.explorer(null, this::chercherMagasin);
		
		List<Instruction> niveaux = LecteurDeCache.chargerInstructions(461, 88, 1);
		Explorateur.executer(new RemplisseurDeNiveaux(magasins), niveaux);

		List<Instruction> objets = LecteurDeCache.chargerInstructions(461, 5, 1);
		Explorateur.executer(new RemplisseurDObjets(magasins), objets);
		
		
		return magasins;
	}
	
	
	public void chercherMagasin(MapGeneral map, Evenement event, Page page) {
		Explorateur.executer(new ChercheurDeMagasinDansPage(map, new ReferenceMap(map, event, page), magasins), page.instructions);
	}


	@Override
	public void run() {
		chercher();
		
		System.out.println(magasins.values().stream().map(mag -> mag.getMagasinCompact()).collect(Collectors.joining("\n")));
		
		magasins.values().forEach(magasin -> {
			System.out.println();
			System.out.print(magasin.getMagasinComplet());
			
			
		});
		
		
	}
}
