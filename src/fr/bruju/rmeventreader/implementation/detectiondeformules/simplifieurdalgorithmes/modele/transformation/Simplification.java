package fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.transformation;

import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.algorithme.Algorithme;
import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.transformation.Transformateur;

public interface Simplification extends Transformateur {
	public Algorithme simplifier(Algorithme algorithme);


	@Override
	default void accept(Transformateur.Visiteur visiteur) {
		visiteur.visit(this);
	}
}
