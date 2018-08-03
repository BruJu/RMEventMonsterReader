package fr.bruju.rmeventreader.implementation.recomposeur.composant;

import fr.bruju.rmeventreader.implementation.recomposeur.composant.visiteur.Visiteur;

public interface Element {

	void accept(Visiteur visiteur);

	
}
