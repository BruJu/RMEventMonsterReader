package fr.bruju.rmeventreader.implementation.recomposeur.composant.composantvariadique;

import java.util.List;

import fr.bruju.rmeventreader.implementation.recomposeur.composant.Element;

/**
 * Element dans une expression variadique
 * 
 * @author Bruju
 *
 * @param <T> Le type de variadique utilisant cet élément
 */
public interface ComposantVariadique extends Element {
	boolean cumuler(List<ComposantVariadique> nouveauxComposants);
}
