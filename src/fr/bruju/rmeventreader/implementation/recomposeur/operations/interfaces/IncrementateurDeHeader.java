package fr.bruju.rmeventreader.implementation.recomposeur.operations.interfaces;

import fr.bruju.rmeventreader.actionmakers.composition.composant.valeur.Algorithme;
import fr.bruju.rmeventreader.implementation.recomposeur.formulededegats.GroupeDeConditions;

public interface IncrementateurDeHeader {

	GroupeDeConditions getGroupe();

	Algorithme getResultat();
	
	
}