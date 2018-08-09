package fr.bruju.rmeventreader.implementation.recomposeur.operations.interfaces;

import fr.bruju.rmeventreader.actionmakers.composition.composant.valeur.Algorithme;
import fr.bruju.rmeventreader.implementation.recomposeur.formulededegats.Header;

public interface StructureDInjectionDeHeader {
	public IncrementateurDeHeader creerIncrementateur(Header head, Algorithme algo);
}
