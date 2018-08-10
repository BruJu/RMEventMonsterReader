package fr.bruju.rmeventreader.implementation.recomposeur.operations.interfaces;

import fr.bruju.rmeventreader.actionmakers.composition.composant.valeur.Algorithme;
import fr.bruju.rmeventreader.implementation.recomposeur.exploitation.Statistique;
import fr.bruju.rmeventreader.implementation.recomposeur.formulededegats.GroupeDeConditions;
import fr.bruju.rmeventreader.utilitaire.Pair;

public interface StructureDInjectionDeHeader {
	public IncrementateurDeHeader creerIncrementateur(Statistique stat, Algorithme algo);
}
