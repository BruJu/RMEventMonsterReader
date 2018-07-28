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
	 * 
	 * @return Une représentation texuelle du composant
	 */
	public String getString();

	/* ========
	 * VISITEUR
	 * ======== */

	/**
	 * Accepte d'être visité par le composant. (Patron de conception Visiteur)
	 * 
	 * @param visiteurDeComposants Le visiteur souhaitant explorer le composant
	 */
	void accept(VisiteurDeComposants visiteurDeComposants);

	/* =================
	 * EVALUATION RAPIDE
	 * ================= */

	/**
	 * Permet de simplifier rapidement les composants.
	 * Cette fonction n'est pas récursive.
	 * <p>
	 * Exemple d'implémentation pour CSwitch
	 * 
	 * <pre>
	 * {@code
	 * if (interrupteur instanceof BConstant) {
	 * 	BConstant cst = (BConstant) interrupteur;
	 * 	return CFixe.get(cst.value == valeur);
	 * }
	 * 
	 * return this;}
	 * </code>
	 * 
	 * @return Une simplification du composant si c'est possible, ce composant sinon
	 */
	Composant evaluationRapide();

}
