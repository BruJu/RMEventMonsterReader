package fr.bruju.rmeventreader.implementation.formulatracker.simplification.inclusion.gestionnairesdeconditions;

import fr.bruju.rmeventreader.implementation.formulatracker.formule.condition.CVariable;
import fr.bruju.rmeventreader.implementation.formulatracker.simplification.inclusion.IntegreurDeCondition;

public class GestionnaireVariableDifferent implements GestionnaireDeCondition {

	private IntegreurDeCondition integreur;
	private CVariable base;

	public GestionnaireVariableDifferent(IntegreurDeCondition integreur, CVariable cVariable) {
		this.integreur = integreur;
		this.base = cVariable;
	}

	@Override
	public IntegreurDeCondition getIntegreur() {
		return integreur;
	}

}
