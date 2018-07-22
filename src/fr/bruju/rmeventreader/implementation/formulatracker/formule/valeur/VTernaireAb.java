package fr.bruju.rmeventreader.implementation.formulatracker.formule.valeur;

import fr.bruju.rmeventreader.implementation.formulatracker.formule.ComposantTernaireAbregre;
import fr.bruju.rmeventreader.implementation.formulatracker.formule.condition.ConditionGauche;

public class VTernaireAb extends ComposantTernaireAbregre<Valeur> implements Valeur {

	public VTernaireAb(ConditionGauche<Valeur> condition, Valeur valeur) {
		super(condition, valeur);
	}

}
