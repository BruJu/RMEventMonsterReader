package fr.bruju.rmeventreader.implementation.formulatracker.simplification.inclusion.gestionnairesdeconditions;

import fr.bruju.rmeventreader.implementation.formulatracker.formule.condition.CVariable;
import fr.bruju.rmeventreader.implementation.formulatracker.simplification.inclusion.IntegreurDeCondition;

public class GestionnaireVariableSuperieur implements GestionnaireDeCondition {

	private IntegreurDeCondition integreur;
	private CVariable base;

	public GestionnaireVariableSuperieur(IntegreurDeCondition integreur, CVariable cVariable, boolean b) {
		this.integreur = integreur;
		this.base = cVariable;
	}

	@Override
	public IntegreurDeCondition getIntegreur() {
		return integreur;
	}

}
