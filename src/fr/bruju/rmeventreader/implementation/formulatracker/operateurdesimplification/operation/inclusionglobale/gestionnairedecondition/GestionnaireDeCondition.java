package fr.bruju.rmeventreader.implementation.formulatracker.operateurdesimplification.operation.inclusionglobale.gestionnairedecondition;

import fr.bruju.rmeventreader.implementation.formulatracker.composant.condition.CArme;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.condition.CSwitch;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.condition.CVariable;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.condition.Condition;

/**
 * 
 * 
 * Un gestionnaire de condition renvoie :
 * - CondVrai si la condition est moins restrictive que soit et qu'elle est toujours vérifiée
 * - CondFaux si la condition ne peut jamais être vérifiée
 * - La condition reçue si 
 * 
 */
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
