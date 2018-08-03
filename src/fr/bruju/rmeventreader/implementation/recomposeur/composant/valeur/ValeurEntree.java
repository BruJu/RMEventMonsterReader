package fr.bruju.rmeventreader.implementation.recomposeur.composant.valeur;

import fr.bruju.rmeventreader.implementation.recomposeur.composant.visiteur.Visiteur;

public class ValeurEntree implements Valeur {
	public final int id;

	public ValeurEntree(int id) {
		this.id = id;
	}
	

	@Override
	public void accept(Visiteur visiteur) {
		visiteur.visit(this);
	}
	
}
