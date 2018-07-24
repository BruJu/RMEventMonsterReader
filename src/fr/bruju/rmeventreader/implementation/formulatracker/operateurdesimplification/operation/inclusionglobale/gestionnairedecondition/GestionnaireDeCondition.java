package fr.bruju.rmeventreader.implementation.formulatracker.operateurdesimplification.operation.inclusionglobale.gestionnairedecondition;

import fr.bruju.rmeventreader.implementation.formulatracker.composant.condition.CArme;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.condition.CSwitch;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.condition.CVariable;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.condition.Condition;

public interface GestionnaireDeCondition {
	public default Condition conditionArme(CArme cArme) {
		return cArme;
	}
	
	public default Condition conditionSwitch(CSwitch cSwitch) {
		return cSwitch;
	}
	
	public default Condition conditionVariable(CVariable cVariable) {
		return cVariable;
	}
}
