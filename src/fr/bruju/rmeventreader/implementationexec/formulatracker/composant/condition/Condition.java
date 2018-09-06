package fr.bruju.rmeventreader.implementationexec.formulatracker.composant.condition;

import fr.bruju.rmeventreader.implementationexec.formulatracker.composant.Composant;

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
