package fr.bruju.rmeventreader.implementation.formulatracker.simplification.inclusion.gestionnairesdeconditions;

import fr.bruju.rmeventreader.implementation.formulatracker.formule.condition.CArme;
import fr.bruju.rmeventreader.implementation.formulatracker.simplification.inclusion.Integreur;

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
	public void conditionArme(CArme cArme) {
		if (cArme.heros != base.heros || cArme.objet != base.objet) {
			integreur.refuse(cArme);
			return;
		}
		
		integreur.gestionnairePush(null, base.haveToOwn == cArme.haveToOwn);
	}
}
