package fr.bruju.rmeventreader.implementation.formulatracker.formule;

import fr.bruju.rmeventreader.implementation.formulatracker.formule.condition.Condition;

public interface ComposantTernaire<T extends Composant> extends Composant {
	@Override
	public default String getString() {
		return "(" + getCondition().getString() + ") ? " + getVrai().getString() + " : " + getFaux().getString(); 
	}
	
	public Condition getCondition();
	
	public T getVrai();
	
	public T getFaux();
}
