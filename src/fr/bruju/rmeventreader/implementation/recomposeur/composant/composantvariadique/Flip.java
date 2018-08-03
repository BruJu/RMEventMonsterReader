package fr.bruju.rmeventreader.implementation.recomposeur.composant.composantvariadique;

import fr.bruju.rmeventreader.implementation.recomposeur.composant.bouton.Bouton;
import fr.bruju.rmeventreader.implementation.recomposeur.composant.visiteur.Visiteur;

public class Flip implements ComposantVariadique<Bouton> {
	
	
	

	@Override
	public void accept(Visiteur visiteur) {
		visiteur.visit(this);
	}
	
	

}
