package fr.bruju.rmeventreader.implementation.recomposeur.arbre;

import java.util.List;
import java.util.function.Function;
import java.util.function.UnaryOperator;

import fr.bruju.rmeventreader.actionmakers.composition.composant.valeur.Algorithme;

public class Contenant {
	private Contenu contenu;
	
	public Contenant(List<Algorithme> contenu) {
		this.contenu = new ListAlgo(this, contenu);
	}
	
	public void transformerAlgorithmes(UnaryOperator<Algorithme> transformation) {
		contenu.transformerAlgorithmes(transformation);
	}
	
	public void ajouterUnNiveau(Function<ListAlgo, Etage> transformation) {
		contenu.ajouterUnNiveau(transformation);
	}
	
	public void transformerContenu(Contenu contenu) {
		this.contenu = contenu;
	}
}
