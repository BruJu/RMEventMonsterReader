package fr.bruju.rmeventreader.implementation.formulatracker.formule;

import fr.bruju.rmeventreader.implementation.formulatracker.simplification.VisiteurDeComposants;

public interface Composant {
	
	public String getString();

	public void accept(VisiteurDeComposants visiteurDeComposants);

}
