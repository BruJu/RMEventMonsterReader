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

/**
 * Cherche les références à des variables codées en dur dans tout un projet
 * @author Bruju
 *
 */
public class ChercheurDeReferences implements Runnable {
	/** Liste des références pour chaque variable */
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
	
	/**
	 * Affiche la liste des références pour la varible
	 * @param variable La variable
	 * @param references sa liste de références
	 */
	private static void afficher(Integer variable, HashSet<Reference> references) {
		System.out.println("==" + variable + "==");
		
		references.forEach(reference -> System.out.println(reference.getString()));
		
		System.out.println();
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
		
		Chercheur chercheur = new Chercheur(ref, variablesCherchees);
		DechiffreurInstructions dechiffreur = new DechiffreurInstructions(chercheur);
		dechiffreur.executer(instructions);
	}
	


}
