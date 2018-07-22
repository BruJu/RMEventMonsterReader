package fr.bruju.rmeventreader.implementation.formulatracker.formule.condition;

public interface ConditionGauche<T> extends Condition {

	public T getGauche();
	
	public String getStringSansGauche();
	
}
