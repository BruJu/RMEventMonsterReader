package fr.bruju.rmeventreader.implementation.recomposeur.composant.condition;

import fr.bruju.rmeventreader.implementation.recomposeur.composant.Element;

public interface Condition extends Element {
	@Override
	public Condition simplifier();

	/**
	 * Inverse la condition
	 * @return La même condition mais inversée
	 */
	public Condition revert();
}
