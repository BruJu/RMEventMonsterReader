package fr.bruju.rmeventreader.implementationexec.chercheurdevariables;

import fr.bruju.rmeventreader.actionmakers.executeur.controlleur.ExecuteurInstructions;

public interface BaseDeRecherche {

	void afficher();

	ExecuteurInstructions getExecuteur(Reference ref);

	
	
	
}
