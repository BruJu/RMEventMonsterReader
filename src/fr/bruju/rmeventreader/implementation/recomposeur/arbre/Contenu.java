package fr.bruju.rmeventreader.implementation.recomposeur.arbre;

import java.util.List;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.UnaryOperator;
import java.util.stream.Stream;

import fr.bruju.rmeventreader.actionmakers.composition.composant.valeur.Algorithme;
import fr.bruju.rmeventreader.implementation.recomposeur.exploitation.Statistique;
import fr.bruju.rmeventreader.implementation.recomposeur.formulededegats.GroupeDeConditions;
import fr.bruju.rmeventreader.implementation.recomposeur.operations.interfaces.StructureDInjectionDeHeader;
import fr.bruju.rmeventreader.utilitaire.Triplet;

public interface Contenu {

	public void transformerAlgorithmes(UnaryOperator<Algorithme> transformation);

	public void ajouterUnNiveau(StructureDInjectionDeHeader transformation);

	public Stream<Triplet<List<GroupeDeConditions>, Statistique, Algorithme>> recupererAlgo();

	void transformerListes(Function<Resultat, ?> classifier, BinaryOperator<Resultat> unifieur);

}
