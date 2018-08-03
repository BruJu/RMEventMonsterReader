package fr.bruju.rmeventreader.implementation.recomposeur.composant;

import fr.bruju.rmeventreader.implementation.recomposeur.composant.visiteur.Visiteur;

public interface Element {

	public void accept(Visiteur visiteur);

	public Element simplifier();
}
