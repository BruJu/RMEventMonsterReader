package fr.bruju.rmeventreader.implementation.formulatracker.composant.etendu;

import fr.bruju.rmeventreader.implementation.formulatracker.composant.visiteur.VisiteurDeComposants;

public interface Composants {

	/* ========
	 * VISITEUR
	 * ======== */

	/**
	 * Accepte d'être visité par le composant. (Patron de conception Visiteur)
	 * @param visiteurDeComposants Le visiteur souhaitant explorer le composant
	 */
	void accept(VisiteurDeComposants visiteurDeComposants);

}
