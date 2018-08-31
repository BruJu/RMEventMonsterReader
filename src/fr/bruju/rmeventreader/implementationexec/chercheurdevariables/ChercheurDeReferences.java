package fr.bruju.rmeventreader.implementationexec.chercheurdevariables;

import java.util.List;

import fr.bruju.rmeventreader.dictionnaires.header.Instruction;
import fr.bruju.rmeventreader.dictionnaires.lecture.Explorateur;

/**
 * Cherche les références à des variables codées en dur dans tout un projet
 * @author Bruju
 *
 */
public class ChercheurDeReferences implements Runnable {
	
	private BaseDeRecherche baseDeRecherche;
	

	@Override
	public void run() {
		int option = 1;
		
		new Runnable[] {
				() -> {baseDeRecherche = new BaseDeRechercheDeVariables(new int[] {961, 962, 963, 964});},
				() -> {baseDeRecherche = new BaseDeRechercheTextuelle("narre");}
		}[option].run();
		
		new Explorateur().explorer(
				ec -> this.explorer(new ReferenceEC(ec.id), ec.instructions),
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
		
		Explorateur.executer(baseDeRecherche.getExecuteur(ref), instructions);
	}
	


}
