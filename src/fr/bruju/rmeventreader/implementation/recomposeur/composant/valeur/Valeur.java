package fr.bruju.rmeventreader.implementation.recomposeur.composant.valeur;

import fr.bruju.rmeventreader.implementation.recomposeur.composant.CaseMemoire;

public interface Valeur extends CaseMemoire {

	@Override
	public default Valeur simplifier() {
		return this;
	}
}
