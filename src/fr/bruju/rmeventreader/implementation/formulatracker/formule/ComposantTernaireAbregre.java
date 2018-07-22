package fr.bruju.rmeventreader.implementation.formulatracker.formule;

import fr.bruju.rmeventreader.implementation.formulatracker.formule.condition.ConditionGauche;

public class ComposantTernaireAbregre<T extends Composant> extends ComposantTernaire<T> {
	

	private ConditionGauche<T> condition2;

	public ComposantTernaireAbregre(ConditionGauche<T> condition, T valeur) {
		super(condition, valeur, condition.getGauche());
		
		this.condition2 = condition;
	}
	
	@Override
	public String getString() {
		return condition2.getGauche().getString() + "•(" + this.condition2.getStringSansGauche() + ", " + this.getVrai().getString();
	}
}
