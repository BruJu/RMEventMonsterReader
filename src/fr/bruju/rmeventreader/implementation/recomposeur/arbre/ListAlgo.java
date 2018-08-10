package fr.bruju.rmeventreader.implementation.recomposeur.arbre;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import fr.bruju.rmeventreader.actionmakers.composition.composant.valeur.Algorithme;
import fr.bruju.rmeventreader.implementation.recomposeur.exploitation.Statistique;
import fr.bruju.rmeventreader.implementation.recomposeur.formulededegats.GroupeDeConditions;
import fr.bruju.rmeventreader.utilitaire.Pair;
import fr.bruju.rmeventreader.utilitaire.Triplet;

public class ListAlgo implements Contenu {
	public final Contenant contenant;
	private List<Resultat> contenu;

	public ListAlgo(Contenant contenant, List<Resultat> contenu) {
		this.contenant = contenant;
		this.contenu = contenu;
	}

	@Override
	public void transformerAlgorithmes(UnaryOperator<Algorithme> transformation) {
		contenu.replaceAll(res -> new Resultat(res.stat, transformation.apply(res.algo)));
	}

	@Override
	public void ajouterUnNiveau(Function<Algorithme, Pair<GroupeDeConditions, Algorithme>> transformation) {
		
		Map<GroupeDeConditions, List<Resultat>> mapResultat = contenu.stream()
			   .map(resultat -> new Pair<>(resultat.stat, transformation.apply(resultat.algo)))
			   .collect(Collectors.groupingBy(pair -> pair.getRight().getLeft(),
					   
					   Collectors.mapping(pair -> new Resultat(pair.getLeft(), pair.getRight().getRight()), 
					   Collectors.toList()
					   )));
		
		
		
		contenant.transformerContenu(new Etage(contenant, null));
		
		
		
	}

	@Override
	public Stream<Triplet<List<GroupeDeConditions>, Statistique, Algorithme>> recupererAlgo() {
		return contenu.stream().map(r -> new Triplet<>(new ArrayList<>(), r.stat, r.algo));
	}

}
