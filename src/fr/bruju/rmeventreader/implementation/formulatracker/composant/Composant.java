package fr.bruju.rmeventreader.implementation.formulatracker.composant;

import fr.bruju.rmeventreader.implementation.formulatracker.composant.visiteur.VisiteurDeComposants;

/**
 * Un composant est un élément qui permet de constituer des formules et représente une donnée quelconque.
 * 
 * @author Bruju
 *
 */
public interface Composant {

	/* ================
	 * AFFICHAGE SIMPLE
	 * ================ */

	/**
	 * Donne une représentation textuelle simple du composant
	 * @return Une représentation texuelle du composant
	 */
	public String getString();
	
	/* ========
	 * VISITEUR
	 * ======== */

	/**
	 * Accepte d'être visité par le composant. (Patron de conception Visiteur)
	 * @param visiteurDeComposants Le visiteur souhaitant explorer le composant
	 */
	public void accept(VisiteurDeComposants visiteurDeComposants);

}
