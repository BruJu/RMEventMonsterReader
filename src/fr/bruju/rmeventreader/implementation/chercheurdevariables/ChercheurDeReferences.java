package fr.bruju.rmeventreader.implementation.chercheurdevariables;

import java.util.List;

import fr.bruju.rmeventreader.actionmakers.executeur.controlleur.Explorateur;
import fr.bruju.rmeventreader.dictionnaires.modele.Instruction;
import fr.bruju.rmeventreader.implementation.chercheurdevariables.module.ApprentissageSort;
import fr.bruju.rmeventreader.implementation.chercheurdevariables.module.ModificationsDeVariable;
import fr.bruju.rmeventreader.implementation.chercheurdevariables.module.ActivationDInterrupteur;
import fr.bruju.rmeventreader.implementation.chercheurdevariables.module.Texte;
import fr.bruju.rmeventreader.implementation.chercheurdevariables.reference.Reference;
import fr.bruju.rmeventreader.implementation.chercheurdevariables.reference.ReferenceEC;
import fr.bruju.rmeventreader.implementation.chercheurdevariables.reference.ReferenceMap;
import fr.bruju.rmeventreader.implementation.chercheurdevariables.module.ApparitionDeVariables;
import fr.bruju.rmeventreader.implementation.chercheurdevariables.module.Musique;

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
		int option = 5;
		
		new Runnable[] {
				/* 0 */ () -> {baseDeRecherche = new ApparitionDeVariables(new int[] {3065});},
				/* 1 */ () -> {baseDeRecherche = new Texte("olinale");},
				/* 2 */ () -> {baseDeRecherche = new ActivationDInterrupteur(3113);},
				/* 3 */ () -> {baseDeRecherche = new Musique();},
				/* 4 */ () -> {baseDeRecherche = new ModificationsDeVariable(2456);},
				/* 5 */ () -> {baseDeRecherche = new ApprentissageSort(3, 112);}
		}[option].run();

		System.out.print("[");
		
		Explorateur.explorer(
				ec -> this.explorer(new ReferenceEC(ec.id, ec.nom), ec.instructions),
				(map, event, page) -> explorer(new ReferenceMap(map, event, page), page.instructions));
		
		System.out.println("]");
		
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
		affichage |= (ref.numero() / (5000 * 25)) > (this.derniereReference / (5000 * 25));
		
		if (affichage) {
			this.derniereReference = (int) ref.numero();
			System.out.print("•");
		}
		
		Explorateur.executer(baseDeRecherche.getExecuteur(ref), instructions);
	}
}
