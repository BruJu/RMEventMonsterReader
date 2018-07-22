package fr.bruju.rmeventreader.implementation.formulatracker.formule.bouton;

import fr.bruju.rmeventreader.implementation.formulatracker.formule.ComposantTernaireAbregre;
import fr.bruju.rmeventreader.implementation.formulatracker.formule.condition.ConditionGauche;

public class BTernaireAb extends ComposantTernaireAbregre<Bouton> implements Bouton {

	public BTernaireAb(ConditionGauche<Bouton> condition, Bouton valeur) {
		super(condition, valeur);
	}

}
