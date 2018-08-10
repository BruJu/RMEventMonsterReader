package fr.bruju.rmeventreader.implementation.recomposeur.operations.interfaces;

import fr.bruju.rmeventreader.implementation.recomposeur.arbre.Resultat;

public interface Unifieur {

	Resultat fusion(Resultat r1, Resultat r2);
	
}
