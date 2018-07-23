package fr.bruju.rmeventreader.implementation.formulatracker.formule;

import fr.bruju.rmeventreader.implementation.formulatracker.formule.condition.Condition;
import java.util.Objects;

public abstract class ComposantTernaire<T extends Composant> implements Composant {
	public final Condition condition;
	public final T siVrai;
	public final T siFaux;

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
		if (siFaux == null) {
			return "(" + getCondition().getString() + ") ? " + getVrai().getString();
		} else {
			return "(" + getCondition().getString() + ") ? " + getVrai().getString() + " : " + getFaux().getString();
		}
	}

	@Override
	public boolean equals(final Object other) {
		if (!(other instanceof ComposantTernaire)) {
			return false;
		}
		ComposantTernaire<?> castOther = (ComposantTernaire<?>) other;
		return Objects.equals(condition, castOther.condition) && Objects.equals(siVrai, castOther.siVrai)
				&& Objects.equals(siFaux, castOther.siFaux);
	}

	@Override
	public int hashCode() {
		return Objects.hash(condition, siVrai, siFaux);
	}
	
	
	
}
