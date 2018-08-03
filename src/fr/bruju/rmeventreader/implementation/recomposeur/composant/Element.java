package fr.bruju.rmeventreader.implementation.recomposeur.composant;

import fr.bruju.rmeventreader.implementation.recomposeur.composant.visiteur.Visiteur;

/**
 * Un élément
 * 
 * @author Bruju
 *
 */
public interface Element {
	/**
	 * Accepte d'être visité par le visiteur
	 * @param visiteur Le visiteur
	 */
	public void accept(Visiteur visiteur);

	/**
	 * Simplifie l'élément dans la mesure du possible
	 * <p>
	 * Par exemple, si cet élément est une suite "affecte 3" "ajoute 2", le transforme en "affecte 5"
	 * @return Cet élément simplifié
	 */
	public Element simplifier();
}
