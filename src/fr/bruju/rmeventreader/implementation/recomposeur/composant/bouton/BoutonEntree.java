package fr.bruju.rmeventreader.implementation.recomposeur.composant.bouton;

import fr.bruju.rmeventreader.implementation.recomposeur.composant.visiteur.Visiteur;

public class BoutonEntree implements Bouton {
	public final int id;

	public BoutonEntree(int id) {
		this.id = id;
	}

	@Override
	public void accept(Visiteur visiteur) {
		visiteur.visit(this);
	}
	
}
