package fr.bruju.rmeventreader.implementation.formulatracker.operateurdesimplification.inclusion.gestionnairedecondition;

import fr.bruju.rmeventreader.implementation.formulatracker.composant.condition.CArme;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.condition.CFixe;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.condition.Condition;

public class GestionnaireArme implements GestionnaireDeCondition {
	private CArme base;
	
	public GestionnaireArme(CArme base) {
		this.base = base;
	}
	
	@Override
	public Condition conditionArme(CArme cArme) {
		if (cArme.heros != base.heros || cArme.objet != base.objet) {
			return cArme;
		}
		
		return CFixe.get(base.haveToOwn == cArme.haveToOwn);
	}
}
