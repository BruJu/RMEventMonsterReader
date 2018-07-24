package fr.bruju.rmeventreader.implementation.formulatracker.operateurdesimplification.operation.inclusionglobale.gestionnairedecondition;

import fr.bruju.rmeventreader.implementation.formulatracker.composant.condition.CArme;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.condition.CFixe;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.condition.Condition;
import fr.bruju.rmeventreader.implementation.formulatracker.operateurdesimplification.operation.inclusionglobale.Integreur;

public class GestionnaireArme implements GestionnaireDeCondition {
	private Integreur integreur;
	
	private CArme base;
	
	public GestionnaireArme(Integreur integreur, CArme base) {
		this.integreur = integreur;
		this.base = base;
	}
	
	@Override
	public Integreur getIntegreur() {
		return integreur;
	}

	@Override
	public Condition conditionArme(CArme cArme) {
		if (cArme.heros != base.heros || cArme.objet != base.objet) {
			integreur.refuse(cArme);
			return cArme;
		}
		
		integreur.gestionnairePush(null, base.haveToOwn == cArme.haveToOwn);
		return CFixe.get(base.haveToOwn == cArme.haveToOwn);
	}
}
