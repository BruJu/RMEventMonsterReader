package fr.bruju.rmeventreader.implementation.recomposeur.composant.valeur;

import fr.bruju.rmeventreader.implementation.recomposeur.composant.visiteur.Visiteur;

public class ValeurConstante implements Valeur {
	public final int valeur;

	public ValeurConstante(int valeur) {
		this.valeur = valeur;
	}
	

	@Override
	public void accept(Visiteur visiteur) {
		visiteur.visit(this);
	}
	
}
