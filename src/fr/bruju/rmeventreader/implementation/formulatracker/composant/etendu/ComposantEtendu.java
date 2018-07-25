package fr.bruju.rmeventreader.implementation.formulatracker.composant.etendu;

import fr.bruju.rmeventreader.implementation.formulatracker.composant.Composant;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.etendu.visiteur.VisiteurEtendu;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.visiteur.VisiteurDeComposants;

public interface ComposantEtendu extends Composants {

	/* ========
	 * VISITEUR
	 * ======== */

	@Override
	public default void accept(VisiteurDeComposants visiteurDeComposants) {
		getComposantNormal().accept(visiteurDeComposants);
	}
	
	public void acceptEtendu(VisiteurEtendu visiteurEtendu);

	/* =========
	 * EXTENSION
	 * ========= */

	public Composant getComposantNormal();
}
