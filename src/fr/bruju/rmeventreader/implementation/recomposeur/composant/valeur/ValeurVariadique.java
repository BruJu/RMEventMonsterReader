package fr.bruju.rmeventreader.implementation.recomposeur.composant.valeur;

import java.util.ArrayList;
import java.util.List;

import fr.bruju.rmeventreader.implementation.recomposeur.composant.composantvariadique.ComposantVariadique;
import fr.bruju.rmeventreader.implementation.recomposeur.composant.visiteur.Visiteur;

public class ValeurVariadique implements Valeur {
	public final List<ComposantVariadique<Valeur>> composants;		// A la fin -> Collections.unmodifiableList(list);
	
	public ValeurVariadique() {
		this.composants = new ArrayList<>();
	}
	
	
	
	public ValeurVariadique simplifier() {
		return this;
	}
	

	@Override
	public void accept(Visiteur visiteur) {
		visiteur.visit(this);
	}
}
