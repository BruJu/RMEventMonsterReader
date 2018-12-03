package fr.bruju.rmeventreader.implementation.chercheurdevariables;


import static fr.bruju.rmeventreader.ProjetS.PROJET;

import java.util.function.Supplier;

import fr.bruju.rmdechiffreur.ExecuteurInstructions;
import fr.bruju.rmdechiffreur.reference.Reference;
import fr.bruju.rmeventreader.implementation.chercheurdevariables.module.*;

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

	public ChercheurDeReferences() {
		int option = 7;

		baseDeRecherche = (BaseDeRecherche) (new Supplier[] {
				/* 0 */ () -> new ApparitionDeVariables(new int[] {3065}),
				/* 1 */ () -> new Texte("essager"),
				/* 2 */ () -> new ActivationDInterrupteur(3113),
				/* 3 */ () -> new Musique(),
				/* 4 */ () -> new ModificationsDeVariable(5),
				/* 5 */ () -> new ApprentissageSort(3, 112),
				/* 6 */ () -> new AppelAUnEvenement(356),
				/* 7 */ () -> new ObjetObtenu(673)
		}[option].get());
	}

	public ChercheurDeReferences(BaseDeRecherche baseDeRecherche) {
		this.baseDeRecherche = baseDeRecherche;
	}


	@Override
	public void run() {
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
