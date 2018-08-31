package fr.bruju.rmeventreader.implementationexec.chercheurdevariables;

import java.util.HashMap;
import java.util.HashSet;

import fr.bruju.rmeventreader.actionmakers.executeur.controlleur.ExecuteurInstructions;

public class BaseDeRechercheDeVariables implements BaseDeRecherche {
	/** Liste des références pour chaque variable */
	private HashMap<Integer, HashSet<Reference>> variablesCherchees = new HashMap<>();

	public BaseDeRechercheDeVariables(int[] is) {
		
		for (int id : is) {
			variablesCherchees.put(id, new HashSet<>());
		}
		
		
	}

	@Override
	public void afficher() {
		variablesCherchees.forEach(BaseDeRechercheDeVariables::afficher);
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

	@Override
	public ExecuteurInstructions getExecuteur(Reference ref) {
		return new Chercheur(ref, variablesCherchees);
	}
	
	
}
