package fr.bruju.rmeventreader.implementation.recomposeur.composant.bouton;

import java.util.ArrayList;
import java.util.List;

import fr.bruju.rmeventreader.implementation.recomposeur.composant.composantvariadique.ComposantVariadique;
import fr.bruju.rmeventreader.implementation.recomposeur.composant.visiteur.Visiteur;

public class BoutonVariadique implements Bouton {
	public final List<ComposantVariadique<Bouton>> composants;		// A la fin -> Collections.unmodifiableList(list);
	
	public BoutonVariadique() {
		this.composants = new ArrayList<>();
	}

	@Override
	public void accept(Visiteur visiteur) {
		visiteur.visit(this);
	}

	
	@Override
	public Bouton simplifier() {
		return this;
	}
	
	
}
