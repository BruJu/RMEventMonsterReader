package fr.bruju.rmeventreader.implementationexec.magasin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import fr.bruju.rmeventreader.dictionnaires.header.Evenement;
import fr.bruju.rmeventreader.dictionnaires.header.Instruction;
import fr.bruju.rmeventreader.dictionnaires.header.MapGeneral;
import fr.bruju.rmeventreader.dictionnaires.header.Page;
import fr.bruju.rmeventreader.dictionnaires.lecture.Explorateur;
import fr.bruju.rmeventreader.dictionnaires.liblcfreader.LecteurDeCache;
import fr.bruju.rmeventreader.implementationexec.chercheurdevariables.ReferenceMap;

public class ChercheurDeMagasins implements Runnable {
	private Map<Integer, Magasin> magasins;
	
	
	public Map<Integer, Magasin> chercher() {
		magasins = new HashMap<>();
		
		new Explorateur().explorer(null, this::chercherMagasin);
		
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