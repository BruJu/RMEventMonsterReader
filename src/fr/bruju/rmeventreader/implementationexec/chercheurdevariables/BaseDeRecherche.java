package fr.bruju.rmeventreader.implementationexec.chercheurdevariables;

import fr.bruju.rmeventreader.actionmakers.executeur.controlleur.ExecuteurInstructions;

/**
 * Interface définissant les objets centralisant les résultats obtenus lors d'une recherche
 * 
 * @author Bruju
 *
 */
public interface BaseDeRecherche {
	/** Affiche les résultats de la recherche */
	public void afficher();

	/** Donne l'exécuteur d'instructions qui traite de l'évènement ayant la référence donnée */
	public ExecuteurInstructions getExecuteur(Reference ref);
}
