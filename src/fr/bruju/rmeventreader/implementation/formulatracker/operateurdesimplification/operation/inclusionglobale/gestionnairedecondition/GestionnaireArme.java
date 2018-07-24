package fr.bruju.rmeventreader.implementation.formulatracker.operateurdesimplification.operation.inclusionglobale.gestionnairedecondition;

import fr.bruju.rmeventreader.implementation.formulatracker.composant.condition.CArme;
import fr.bruju.rmeventreader.implementation.formulatracker.operateurdesimplification.operation.inclusionglobale.Integreur;
import fr.bruju.rmeventreader.utilitaire.Pair;

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
	public Pair<CArme, Boolean> conditionArme(CArme cArme) {
		if (cArme.heros != base.heros || cArme.objet != base.objet) {
			integreur.refuse(cArme);
			return new Pair<>(cArme, null);
		}
		
		integreur.gestionnairePush(null, base.haveToOwn == cArme.haveToOwn);
		return new Pair<>(null, base.haveToOwn == cArme.haveToOwn);
	}
}
