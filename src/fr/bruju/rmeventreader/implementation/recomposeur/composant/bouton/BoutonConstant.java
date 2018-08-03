package fr.bruju.rmeventreader.implementation.recomposeur.composant.bouton;

import fr.bruju.rmeventreader.implementation.recomposeur.composant.visiteur.Visiteur;

public class BoutonConstant implements Bouton {
	public final boolean valeur;

	public BoutonConstant(boolean valeur) {
		this.valeur = valeur;
	}

	@Override
	public void accept(Visiteur visiteur) {
		visiteur.visit(this);
	}
	
	
	
	
}
