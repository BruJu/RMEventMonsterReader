package fr.bruju.rmeventreader.implementation.formulatracker.operateurdesimplification.operation.inclusionglobale.gestionnairedecondition;

import fr.bruju.rmeventreader.implementation.formulatracker.composant.condition.CArme;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.condition.CSwitch;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.condition.CVariable;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.condition.Condition;
import fr.bruju.rmeventreader.implementation.formulatracker.operateurdesimplification.operation.inclusionglobale.Integreur;

public interface GestionnaireDeCondition {

	public default Condition conditionArme(CArme cArme) {
		getIntegreur().refuse(cArme);
		return cArme;
	}
	public default Condition conditionSwitch(CSwitch cSwitch) {
		getIntegreur().refuse(cSwitch);
		return cSwitch;
	}
	
	public default Condition conditionVariable(CVariable cVariable) {
		getIntegreur().refuse(cVariable);
		return cVariable;
	}

	public Integreur getIntegreur();
}
