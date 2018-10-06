package fr.bruju.rmeventreader.implementation.detectiondeformules.recomposeur.operations.interfaces;

import fr.bruju.rmeventreader.implementation.detectiondeformules.recomposeur.composant.valeur.Algorithme;
import fr.bruju.rmeventreader.implementation.detectiondeformules.recomposeur.exploitation.Statistique;
import fr.bruju.rmeventreader.implementation.detectiondeformules.recomposeur.formulededegats.GroupeDeConditions;
import fr.bruju.rmeventreader.utilitaire.Pair;

public interface StructureDInjectionDeHeader {
	public Iterable<Pair<GroupeDeConditions, Algorithme>> degrouper(Statistique stat, Algorithme algo);
	
	public String getNom();
}
