package fr.bruju.rmeventreader.implementation.formulatracker.operateurdesimplification.operation.inclusionglobale.gestionnairedecondition;

import fr.bruju.rmeventreader.implementation.formulatracker.composant.condition.CArme;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.condition.CSwitch;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.condition.CVariable;
import fr.bruju.rmeventreader.implementation.formulatracker.operateurdesimplification.operation.inclusionglobale.Integreur;
import fr.bruju.rmeventreader.utilitaire.Pair;

public interface GestionnaireDeCondition {

	public default Pair<CArme, Boolean> conditionArme(CArme cArme) {
		getIntegreur().refuse(cArme);
		return new Pair<>(cArme, null);
	}
	public default Pair<CSwitch, Boolean> conditionSwitch(CSwitch cSwitch) {
		getIntegreur().refuse(cSwitch);
		return new Pair<>(cSwitch, null);
	}
	
	public default Pair<CVariable, Boolean> conditionVariable(CVariable cVariable) {
		getIntegreur().refuse(cVariable);
		return new Pair<>(cVariable, null);
	}

	public Integreur getIntegreur();
}
