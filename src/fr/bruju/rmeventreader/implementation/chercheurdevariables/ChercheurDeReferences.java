package fr.bruju.rmeventreader.implementation.chercheurdevariables;

import java.util.List;

import fr.bruju.rmeventreader.actionmakers.executeur.controlleur.Explorateur;
import fr.bruju.rmeventreader.dictionnaires.modele.Instruction;

/**
 * Cherche les références à des variables codées en dur dans tout un projet
 * @author Bruju
 *
 */
public class ChercheurDeReferences implements Runnable {
	/** Base de recherche */
	private BaseDeRecherche baseDeRecherche;

	/** Derniere reference */
	private int derniereReference = -1;

	@Override
	public void run() {
		int option = 4;
		
		new Runnable[] {
				() -> {baseDeRecherche = new BaseDeRechercheDeVariables(new int[] {961, 962, 963, 964});},
				() -> {baseDeRecherche = new BaseDeRechercheTextuelle("narre");},
				() -> {baseDeRecherche = new BaseDeRechercheVarAAOn(128);},
				() -> {baseDeRecherche = new BaseDeRechercheMusique();},
				() -> {baseDeRecherche = new BaseValeursAffectees(2972);}
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
		boolean affichage;
		affichage = ref.numero() > 0 && this.derniereReference < 0;
		affichage |= (ref.numero() / (5000 * 100)) > (this.derniereReference / (5000 * 100));
		
		if (affichage) {
			this.derniereReference = (int) ref.numero();
			System.out.println("REFERENCE [" + ref.getString() + "]");
		}
		
		Explorateur.executer(baseDeRecherche.getExecuteur(ref), instructions);
	}
}
