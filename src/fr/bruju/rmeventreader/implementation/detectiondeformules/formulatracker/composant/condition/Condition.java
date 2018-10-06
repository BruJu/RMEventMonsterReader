package fr.bruju.rmeventreader.implementation.detectiondeformules.formulatracker.composant.condition;

import fr.bruju.rmeventreader.implementation.detectiondeformules.formulatracker.composant.Composant;

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
