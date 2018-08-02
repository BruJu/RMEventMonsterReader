package fr.bruju.rmeventreader.implementation.recomposeur.composant.valeur;

import java.util.ArrayList;
import java.util.List;

import fr.bruju.rmeventreader.implementation.recomposeur.composant.composantvariadique.ComposantVariadique;

public class ValeurVariadique implements ValeurDerivee {
	public final List<ComposantVariadique<Valeur>> composants;		// A la fin -> Collections.unmodifiableList(list);
	
	public ValeurVariadique() {
		this.composants = new ArrayList<>();
	}
	
	
}
