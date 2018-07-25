package fr.bruju.rmeventreader.implementation.formulatracker.composant;

import fr.bruju.rmeventreader.implementation.formulatracker.composant.visiteur.VisiteurDeComposants;

public interface ComposantEtendu extends Composants {

	/* ========
	 * VISITEUR
	 * ======== */

	@Override
	public default void accept(VisiteurDeComposants visiteurDeComposants) {
		getComposantNormal().accept(visiteurDeComposants);
	}

	/* =========
	 * EXTENSION
	 * ========= */

	public Composant getComposantNormal();
}
