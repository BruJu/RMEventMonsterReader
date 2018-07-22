package fr.bruju.rmeventreader.implementation.formulatracker.formule;

import fr.bruju.rmeventreader.implementation.formulatracker.formule.condition.Condition;

public abstract class ComposantTernaire<T extends Composant> implements Composant {
	private Condition condition;
	private T siVrai;
	private T siFaux;

	public ComposantTernaire(Condition condition, T v1, T v2) {
		this.condition = condition;
		this.siVrai = v1;
		this.siFaux = v2;
	}

	public Condition getCondition() {
		return condition;
	}

	public T getVrai() {
		return siVrai;
	}

	public T getFaux() {
		return siFaux;
	}

	@Override
	public String getString() {		
		return "(" + getCondition().getString() + ") ? " + getVrai().getString() + " : " + getFaux().getString(); 
	}
}
