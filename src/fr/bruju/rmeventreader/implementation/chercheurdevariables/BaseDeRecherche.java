package fr.bruju.rmeventreader.implementation.chercheurdevariables;

import fr.bruju.rmeventreader.actionmakers.controlleur.ExecuteurInstructions;
import fr.bruju.rmeventreader.actionmakers.reference.Reference;

/**
 * Interface définissant les objets centralisant les résultats obtenus lors d'une recherche
 * 
 * @author Bruju
 *
 */
public interface BaseDeRecherche {
	/** Donne l'exécuteur d'instructions qui traite de l'évènement ayant la référence donnée */
	public ExecuteurInstructions getExecuteur(Reference ref);
	
	/** Affiche les résultats de la recherche */
	public void afficher();
}
	