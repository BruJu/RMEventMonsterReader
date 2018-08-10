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

public class Contenant implements Contenu {
	private Contenu contenu;
	
	public Contenant() {}
	
	public Contenant(List<Resultat> contenu) {
		this.contenu = new ListAlgo(this, contenu);
	}
	
	@Override
	public void transformerAlgorithmes(UnaryOperator<Algorithme> transformation) {
		contenu.transformerAlgorithmes(transformation);
	}
	
	public void transformerContenu(Contenu contenu) {
		this.contenu = contenu;
	}

	@Override
	public Stream<Triplet<List<GroupeDeConditions>, Statistique, Algorithme>> recupererAlgo() {
		return contenu.recupererAlgo();
	}

	@Override
	public void ajouterUnNiveau(StructureDInjectionDeHeader transformation) {
		contenu.ajouterUnNiveau(transformation);
	}

	@Override
	public void transformerListes(Function<Resultat, ?> classifier, BinaryOperator<Resultat> unifieur) {
		contenu.transformerListes(classifier, unifieur);
	}
}