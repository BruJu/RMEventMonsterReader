package fr.bruju.rmeventreader.implementation.formulatracker.composant;

import fr.bruju.rmeventreader.implementation.formulatracker.composant.etendu.Composants;

/**
 * Un composant est un élément qui permet de constituer des formules et représente une donnée quelconque.
 * 
 * @author Bruju
 *
 */
public interface Composant extends Composants {

	/* ================
	 * AFFICHAGE SIMPLE
	 * ================ */

	/**
	 * Donne une représentation textuelle simple du composant
	 * @return Une représentation texuelle du composant
	 */
	public String getString();
}
