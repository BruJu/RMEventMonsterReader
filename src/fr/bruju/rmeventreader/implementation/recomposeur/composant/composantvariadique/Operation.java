package fr.bruju.rmeventreader.implementation.recomposeur.composant.composantvariadique;

import fr.bruju.rmeventreader.implementation.recomposeur.composant.valeur.Valeur;
import fr.bruju.rmeventreader.implementation.recomposeur.composant.visiteur.Visiteur;

public class Operation implements ComposantVariadique<Valeur> {

	

	@Override
	public void accept(Visiteur visiteur) {
		visiteur.visit(this);
	}
}
