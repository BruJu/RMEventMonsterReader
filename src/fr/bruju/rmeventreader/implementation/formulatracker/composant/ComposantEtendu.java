package fr.bruju.rmeventreader.implementation.formulatracker.composant;

import fr.bruju.rmeventreader.implementation.formulatracker.composant.visiteur.VisiteurDeComposants;

public interface ComposantEtendu extends Composant {
	@Override
	default void accept(VisiteurDeComposants visiteurDeComposants) {
		visiteurDeComposants.visit(this);
	}
}
