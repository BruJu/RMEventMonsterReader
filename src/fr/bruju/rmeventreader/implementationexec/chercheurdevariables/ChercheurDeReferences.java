package fr.bruju.rmeventreader.implementationexec.chercheurdevariables;

import java.util.List;

import fr.bruju.rmeventreader.actionmakers.executeur.controlleur.Explorateur;
import fr.bruju.rmeventreader.dictionnaires.header.Instruction;

/**
 * Cherche les références à des variables codées en dur dans tout un projet
 * @author Bruju
 *
 */
public class ChercheurDeReferences implements Runnable {
	
	private BaseDeRecherche baseDeRecherche;
	

	@Override
	public void run() {
		int option = 2;
		
		new Runnable[] {
				() -> {baseDeRecherche = new BaseDeRechercheDeVariables(new int[] {961, 962, 963, 964});},
				() -> {baseDeRecherche = new BaseDeRechercheTextuelle("narre");},
				() -> {baseDeRecherche = new BaseDeRechercheVarAAOn(128);}
		}[option].run();
		
		Explorateur.explorer(
				ec -> this.explorer(new ReferenceEC(ec.id, ec.nom), ec.instructions),
				(map, event, page) -> explorer(new ReferenceMap(map, event, page), page.instructions));
		
		baseDeRecherche.afficher();	
	}
	

	
	/**
	 * Cherche des références aux variables et les ajoute si des références sont trouvées
	 * @param ref La référence à ajouter
	 * @param instructions Les instructions à explorer
	 */
	private void explorer(Reference ref, List<Instruction> instructions) {
		//System.out.println("REFERENCE [" + ref.getString() + "]");
		// Explorateur.executer(baseDeRecherche.getExecuteur(ref), instructions);
		
		String[] str = new String[] {
				"Hyurne-Quartier Sud-ouest ",
				"Logo NWP",
				"Hyurne-Quartier Nord-Est"
				
				
		};
		
		if (ref.numero() < 0) {
			Explorateur.executer(baseDeRecherche.getExecuteur(ref), instructions);
		}
		
		for (String s : str) {
			if (ref.getString().contains(s)) {
				Explorateur.executer(baseDeRecherche.getExecuteur(ref), instructions);
			}
			
			
		}
	}
	


}
