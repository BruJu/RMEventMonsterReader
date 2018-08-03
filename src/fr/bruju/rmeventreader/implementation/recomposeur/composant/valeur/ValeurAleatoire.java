package fr.bruju.rmeventreader.implementation.recomposeur.composant.valeur;

import fr.bruju.rmeventreader.implementation.recomposeur.composant.visiteur.Visiteur;

public class ValeurAleatoire implements Valeur {
	public final int min;
	public final int max;
	
	public ValeurAleatoire(int min, int max) {
		this.min = min;
		this.max = max;
	}
	

	@Override
	public void accept(Visiteur visiteur) {
		visiteur.visit(this);
	}
}
