package fr.bruju.rmeventreader.implementation.recomposeur.composant.composantvariadique;

import java.util.List;

import fr.bruju.rmeventreader.implementation.recomposeur.composant.CaseMemoire;
import fr.bruju.rmeventreader.implementation.recomposeur.composant.Element;
import fr.bruju.rmeventreader.implementation.recomposeur.composant.Variadique;

/**
 * Element dans une expression variadique
 * 
 * @author Bruju
 *
 * @param <T> Le type de variadique utilisant cet élément
 */
public interface ComposantVariadique<T extends Variadique<? extends CaseMemoire>> extends Element {
	boolean cumuler(List<ComposantVariadique<? extends T>> nouveauxComposants);
}
