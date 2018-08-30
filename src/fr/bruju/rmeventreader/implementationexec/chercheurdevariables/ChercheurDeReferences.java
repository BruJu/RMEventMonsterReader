package fr.bruju.rmeventreader.implementationexec.chercheurdevariables;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import fr.bruju.rmeventreader.actionmakers.executeur.controlleur.DechiffreurInstructions;
import fr.bruju.rmeventreader.actionmakers.executeur.controlleur.ExecuteurInstructions;
import fr.bruju.rmeventreader.dictionnaires.header.Evenement;
import fr.bruju.rmeventreader.dictionnaires.header.Instruction;
import fr.bruju.rmeventreader.dictionnaires.header.MapGeneral;
import fr.bruju.rmeventreader.dictionnaires.liblcfreader.LecteurDeCache;
import fr.bruju.rmeventreader.utilitaire.Pair;

/**
 * Cherche les références à des variables codées en dur dans tout un projet
 * @author Bruju
 *
 */
public class ChercheurDeReferences implements Runnable {
	
	private BaseDeRecherche baseDeRecherche;
	

	@Override
	public void run() {
		//baseDeRecherche = new BaseDeRechercheDeVariables(new int[] {961, 962, 963, 964});
		baseDeRecherche = new BaseDeRechercheTextuelle("narre");
		
		explorer();
		
		baseDeRecherche.afficher();	
	}
	

	/**
	 * Recherche les références aux variables définies
	 */
	private void explorer() {
		Pair<Integer, List<Integer>> infos = LecteurDeCache.getInformations();
		
		for (int i = 1 ; i <= infos.getLeft() ; i++) { 
			explorerEvenementCommun(i);
		}
		infos.getRight().forEach(numero -> explorerMap(numero));
	}

	/**
	 * Cherche des références aux variables dans la map dont le numéro des donné
	 * @param numero Numéro de la map
	 */
	private void explorerMap(Integer numero) {
		MapGeneral map = LecteurDeCache.getMapGeneral(numero);
		List<Evenement> evenements = 
				LecteurDeCache.getEvenementsDepuisMapGeneral(map);
		
		evenements.forEach(evenement -> {
			evenement.pages.forEach(page -> explorer(new ReferenceMap(numero, evenement.id, page.id, map.map.getNom(), evenement.nom),page.instructions));
		});		
	}

	/**
	 * Cherche des références aux variables dans l'évènement commun donné
	 * @param numero Le numéro de l'évènement
	 */
	private void explorerEvenementCommun(int numero) {
		explorer(new ReferenceEC(numero), LecteurDeCache.getEvenementCommun(numero).instructions);
	}
	
	/**
	 * Cherche des références aux variables et les ajoute si des références sont trouvées
	 * @param ref La référence à ajouter
	 * @param instructions Les instructions à explorer
	 */
	private void explorer(Reference ref, List<Instruction> instructions) {
		//System.out.println("REFERENCE [" + ref.getString() + "]");
		
		ExecuteurInstructions chercheur = baseDeRecherche.getExecuteur(ref);
		DechiffreurInstructions dechiffreur = new DechiffreurInstructions(chercheur);
		dechiffreur.executer(instructions);
	}
	


}
