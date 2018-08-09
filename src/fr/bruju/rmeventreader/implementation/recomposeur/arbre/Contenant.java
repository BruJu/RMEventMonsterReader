package fr.bruju.rmeventreader.implementation.recomposeur.arbre;

import java.util.List;
import java.util.function.Function;
import java.util.function.UnaryOperator;
import java.util.stream.Stream;

import fr.bruju.rmeventreader.actionmakers.composition.composant.valeur.Algorithme;
import fr.bruju.rmeventreader.implementation.recomposeur.exploitation.Statistique;
import fr.bruju.rmeventreader.implementation.recomposeur.formulededegats.GroupeDeConditions;
import fr.bruju.rmeventreader.utilitaire.Triplet;

public class Contenant {
	private Contenu contenu;
	
	public Contenant() {}
	
	public Contenant(List<Resultat> contenu) {
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

	public Stream<Triplet<List<GroupeDeConditions>, Statistique, Algorithme>> recupererAlgo() {
		return contenu.recupererAlgo();
	}
}
