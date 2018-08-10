package fr.bruju.rmeventreader.implementation.recomposeur.operations.interfaces;

import fr.bruju.rmeventreader.actionmakers.composition.composant.valeur.Algorithme;
import fr.bruju.rmeventreader.implementation.recomposeur.exploitation.Statistique;

public interface StructureDInjectionDeHeader {
	public IncrementateurDeHeader creerIncrementateur(Statistique stat, Algorithme algo);
	
	public String getNom();
}
