package fr.bruju.rmeventreader.actionmakers.composition.composant.condition;

import fr.bruju.rmeventreader.actionmakers.composition.composant.Element;
import fr.bruju.rmeventreader.actionmakers.executeur.controlleur.ExtCondition;

public interface Condition extends Element {
	@Override
	public Condition simplifier();

	/**
	 * Inverse la condition
	 * @return La même condition mais inversée
	 */
	public Condition revert();
}
