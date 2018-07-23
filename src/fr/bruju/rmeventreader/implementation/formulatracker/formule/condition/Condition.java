package fr.bruju.rmeventreader.implementation.formulatracker.formule.condition;

import fr.bruju.rmeventreader.implementation.formulatracker.formule.Composant;

/**
 * Représente une condition
 * 
 * @author Bruju
 *
 */
public interface Condition extends Composant {
	/**
	 * Inverse la condition
	 * @return La même condition mais inversée
	 */
	public Condition revert();
}
