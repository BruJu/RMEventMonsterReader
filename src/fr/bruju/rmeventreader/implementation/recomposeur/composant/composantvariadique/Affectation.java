package fr.bruju.rmeventreader.implementation.recomposeur.composant.composantvariadique;

import fr.bruju.rmeventreader.implementation.recomposeur.composant.CaseMemoire;
import fr.bruju.rmeventreader.implementation.recomposeur.composant.visiteur.Visiteur;

public class Affectation<T extends CaseMemoire> implements ComposantVariadique<T> {
	public final T base;
	
	public Affectation(T base) {
		this.base = base;
	}
	
	

	@Override
	public void accept(Visiteur visiteur) {
		visiteur.visit(this);
	}

	@Override
	public Affectation<T> simplifier() {
		return this;
	}
}
