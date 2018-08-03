package fr.bruju.rmeventreader.implementation.recomposeur.composant.bouton;

import java.util.ArrayList;
import java.util.List;

import fr.bruju.rmeventreader.implementation.recomposeur.composant.Variadique;
import fr.bruju.rmeventreader.implementation.recomposeur.composant.composantvariadique.ComposantVariadique;
import fr.bruju.rmeventreader.implementation.recomposeur.composant.visiteur.Visiteur;

/**
 * Représente une série de traitement sur un interrupteur
 * 
 * @author Bruju
 *
 */
public class BoutonVariadique implements Bouton, Variadique<Bouton> {
	public final List<ComposantVariadique<BoutonVariadique>> composants;		// A la fin -> Collections.unmodifiableList(list);
	
	public BoutonVariadique() {
		this.composants = new ArrayList<>();
	}

	@Override
	public void accept(Visiteur visiteur) {
		visiteur.visit(this);
	}

	
	@Override
	public BoutonVariadique simplifier() {
		return this;
	}
	
	
}
