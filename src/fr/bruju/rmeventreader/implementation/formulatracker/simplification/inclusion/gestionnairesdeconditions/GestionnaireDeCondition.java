package fr.bruju.rmeventreader.implementation.formulatracker.simplification.inclusion.gestionnairesdeconditions;

import fr.bruju.rmeventreader.implementation.formulatracker.formule.condition.CArme;
import fr.bruju.rmeventreader.implementation.formulatracker.formule.condition.CSwitch;
import fr.bruju.rmeventreader.implementation.formulatracker.formule.condition.CVariable;
import fr.bruju.rmeventreader.implementation.formulatracker.simplification.inclusion.IntegreurDeCondition;

public interface GestionnaireDeCondition {

	public default void conditionArme(CArme cArme) {
		getIntegreur().refuse(cArme);
		
	}
	public default void conditionSwitch(CSwitch cSwitch) {
		getIntegreur().refuse(cSwitch);
	}
	/*
	 * 		if (enCoursS == null
				|| !enCoursS.interrupteur.equals(cSwitch.interrupteur)) {
			super.visit(cSwitch);
			return;
		}

		// Resolution de la condition
		this.pile.push(null);
		this.conditionFlag = cSwitch.valeur == enCoursS.valeur;
	 * 
	 */
	
	public default void conditionVariable(CVariable cVariable) {
		getIntegreur().refuse(cVariable);
	}

	public IntegreurDeCondition getIntegreur();
	

	
	
	
}
