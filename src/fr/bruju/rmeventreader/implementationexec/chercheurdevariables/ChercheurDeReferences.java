package fr.bruju.rmeventreader.implementationexec.chercheurdevariables;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import fr.bruju.rmeventreader.actionmakers.executeur.controlleur.DechiffreurInstructions;
import fr.bruju.rmeventreader.dictionnaires.header.Evenement;
import fr.bruju.rmeventreader.dictionnaires.header.Instruction;
import fr.bruju.rmeventreader.dictionnaires.header.MapGeneral;
import fr.bruju.rmeventreader.dictionnaires.liblcfreader.LecteurDeCache;
import fr.bruju.rmeventreader.utilitaire.Pair;

public class ChercheurDeReferences implements Runnable {
	private HashMap<Integer, HashSet<Reference>> variablesCherchees = new HashMap<>();

	@Override
	public void run() {
		variablesCherchees.put(961, new HashSet<>());
		variablesCherchees.put(962, new HashSet<>());
		variablesCherchees.put(963, new HashSet<>());
		variablesCherchees.put(964, new HashSet<>());
		
		explorer();
		
		variablesCherchees.forEach(ChercheurDeReferences::afficher);
		
	}
	
	private static void afficher(Integer variable, HashSet<Reference> references) {
		System.out.println("==" + variable + "==");
		
		references.forEach(reference -> System.out.println(reference.getString()));
		
		System.out.println();
	}

	private void explorer() {
		Pair<Integer, List<Integer>> infos = LecteurDeCache.getInformations();
		
		for (int i = 1 ; i <= infos.getLeft() ; i++) { 
			explorerEvenementCommun(i);
		}
		infos.getRight().forEach(numero -> explorerMap(numero));
	}

	private void explorerMap(Integer numero) {
		MapGeneral map = LecteurDeCache.getMapGeneral(numero);
		List<Evenement> evenements = 
				LecteurDeCache.getEvenementsDepuisMapGeneral(map);
		
		evenements.forEach(evenement -> {
			evenement.pages.forEach(page -> explorer(new ReferenceMap(numero, evenement.id, page.id, map.map.getNom(), evenement.nom),page.instructions));
		});		
	}

	private void explorerEvenementCommun(int numero) {
		explorer(new ReferenceEC(numero), LecteurDeCache.getEvenementCommun(numero).instructions);
	}
	
	
	private void explorer(Reference ref, List<Instruction> instructions) {
		System.out.println("REFERENCE [" + ref.getString() + "]");
		
		Chercheur chercheur = new Chercheur(ref, variablesCherchees);
		DechiffreurInstructions dechiffreur = new DechiffreurInstructions(chercheur);
		dechiffreur.executer(instructions);
	}
	


}
