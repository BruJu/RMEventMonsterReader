package fr.bruju.rmeventreader.implementation.recomposeur.arbre;

import java.util.function.Function;
import java.util.function.UnaryOperator;

import fr.bruju.rmeventreader.actionmakers.composition.composant.valeur.Algorithme;

public interface Contenu {

	public void transformerAlgorithmes(UnaryOperator<Algorithme> transformation);

	public void ajouterUnNiveau(Function<ListAlgo, Etage> transformation);

}
