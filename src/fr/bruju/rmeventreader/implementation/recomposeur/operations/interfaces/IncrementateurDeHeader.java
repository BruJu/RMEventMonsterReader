package fr.bruju.rmeventreader.implementation.recomposeur.operations.interfaces;

import fr.bruju.rmeventreader.actionmakers.composition.composant.valeur.Algorithme;
import fr.bruju.rmeventreader.implementation.recomposeur.formulededegats.GroupeDeConditions;
import fr.bruju.rmeventreader.implementation.recomposeur.formulededegats.Header;
import fr.bruju.rmeventreader.utilitaire.Pair;

public interface IncrementateurDeHeader {
	
	/*
	public Header getHeader();
	public Algorithme getResultat();
*/
	
	public Pair<Header, Algorithme> produire();
}