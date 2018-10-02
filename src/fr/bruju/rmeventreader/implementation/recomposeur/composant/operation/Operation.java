package fr.bruju.rmeventreader.implementation.recomposeur.composant.operation;

import java.util.List;

import fr.bruju.rmeventreader.implementation.recomposeur.composant.Element;

/**
 * Element dans une expression variadique
 * 
 * @author Bruju
 *
 * @param <T> Le type de variadique utilisant cet élément
 */
public interface Operation extends Element {
	/**
	 * Cumule l'opérateur actuel dans la liste des opérations déjà présentes
	 * @param nouveauxComposants La liste des opérateurs déjà présentes
	 * @return Faux si cette opération s'est contentée de se rajouter à la liste. Vrai dans tous les autres cas
	 */
	boolean cumuler(List<Operation> nouveauxComposants);
}
