package fr.bruju.rmeventreader.implementation.formulatracker.simplification.inclusion.gestionnairesdeconditions;

import fr.bruju.rmeventreader.implementation.formulatracker.composant.condition.CArme;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.condition.CSwitch;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.condition.CVariable;
import fr.bruju.rmeventreader.implementation.formulatracker.simplification.inclusion.Integreur;

public interface GestionnaireDeCondition {

	public default void conditionArme(CArme cArme) {
		getIntegreur().refuse(cArme);
		
	}
	public default void conditionSwitch(CSwitch cSwitch) {
		getIntegreur().refuse(cSwitch);
	}
	
	public default void conditionVariable(CVariable cVariable) {
		getIntegreur().refuse(cVariable);
	}

	public Integreur getIntegreur();
}