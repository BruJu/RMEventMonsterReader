package fr.bruju.rmeventreader.implementation.recomposeur.composant.composantvariadique;

import fr.bruju.rmeventreader.implementation.recomposeur.composant.CaseMemoire;
import fr.bruju.rmeventreader.implementation.recomposeur.composant.visiteur.Visiteur;

public class Ternaire<T extends CaseMemoire> implements ComposantVariadique<T> {


	@Override
	public void accept(Visiteur visiteur) {
		visiteur.visit(this);
	}
}
