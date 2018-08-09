package fr.bruju.rmeventreader.implementation.recomposeur.arbre;

import java.util.List;
import java.util.function.Function;
import java.util.function.UnaryOperator;

import fr.bruju.rmeventreader.actionmakers.composition.composant.valeur.Algorithme;

public class ListAlgo implements Contenu {
	public final Contenant contenant;
	private List<Algorithme> contenu;

	public ListAlgo(Contenant contenant, List<Algorithme> contenu) {
		this.contenant = contenant;
		this.contenu = contenu;
	}

	@Override
	public void transformerAlgorithmes(UnaryOperator<Algorithme> transformation) {
		contenu.replaceAll(transformation);
	}

	@Override
	public void ajouterUnNiveau(Function<ListAlgo, Etage> transformation) {
		contenant.transformerContenu(transformation.apply(this));
	}
}
