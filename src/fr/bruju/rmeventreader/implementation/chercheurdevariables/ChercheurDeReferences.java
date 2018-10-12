package fr.bruju.rmeventreader.implementation.chercheurdevariables;


import static fr.bruju.rmeventreader.ProjetS.PROJET;

import java.util.function.Supplier;

import fr.bruju.rmdechiffreur.ExecuteurInstructions;
import fr.bruju.rmdechiffreur.reference.Reference;
import fr.bruju.rmeventreader.implementation.chercheurdevariables.module.ActivationDInterrupteur;
import fr.bruju.rmeventreader.implementation.chercheurdevariables.module.ApparitionDeVariables;
import fr.bruju.rmeventreader.implementation.chercheurdevariables.module.AppelAUnEvenement;
import fr.bruju.rmeventreader.implementation.chercheurdevariables.module.ApprentissageSort;
import fr.bruju.rmeventreader.implementation.chercheurdevariables.module.ModificationsDeVariable;
import fr.bruju.rmeventreader.implementation.chercheurdevariables.module.Musique;
import fr.bruju.rmeventreader.implementation.chercheurdevariables.module.Texte;

/**
 * Cherche les références à des variables codées en dur dans tout un projet
 * @author Bruju
 *
 */
public class ChercheurDeReferences implements Runnable {
	/** Base de recherche */
	private BaseDeRecherche baseDeRecherche;

	/** Derniere reference */
	private int dernierGroupe = 0;

	@Override
	public void run() {
		int option = 6;
		
		baseDeRecherche = (BaseDeRecherche) (new Supplier[] {
				/* 0 */ () -> new ApparitionDeVariables(new int[] {3065}),
				/* 1 */ () -> new Texte("olinale"),
				/* 2 */ () -> new ActivationDInterrupteur(3113),
				/* 3 */ () -> new Musique(),
				/* 4 */ () -> new ModificationsDeVariable(5),
				/* 5 */ () -> new ApprentissageSort(3, 112),
				/* 6 */ () -> new AppelAUnEvenement(356)
		}[option].get());

		System.out.print("[");
		PROJET.referencerEvenementsCommuns(baseDeRecherche::getExecuteur);
		System.out.print("•");
		PROJET.referencerCartes(this::explorer);
		System.out.println("]");
		
		baseDeRecherche.afficher();	
	}
	
	/**
	 * Cherche des références aux variables et les ajoute si des références sont trouvées
	 * @param ref La référence à ajouter
	 * @param instructions Les instructions à explorer
	 */
	private ExecuteurInstructions explorer(Reference ref) {
		int groupeDeLaCarte = ref.idCarte() / 25;
		
		if (groupeDeLaCarte > this.dernierGroupe) {
			this.dernierGroupe = ref.idCarte() / 25;
			System.out.print("•");
		}
		
		return baseDeRecherche.getExecuteur(ref);
	}
}
