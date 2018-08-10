package fr.bruju.rmeventreader.implementation.recomposeur.arbre;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import fr.bruju.rmeventreader.actionmakers.composition.composant.valeur.Algorithme;
import fr.bruju.rmeventreader.implementation.recomposeur.exploitation.Statistique;
import fr.bruju.rmeventreader.implementation.recomposeur.formulededegats.GroupeDeConditions;
import fr.bruju.rmeventreader.implementation.recomposeur.operations.interfaces.IncrementateurDeHeader;
import fr.bruju.rmeventreader.implementation.recomposeur.operations.interfaces.StructureDInjectionDeHeader;
import fr.bruju.rmeventreader.utilitaire.Pair;
import fr.bruju.rmeventreader.utilitaire.Triplet;
import fr.bruju.rmeventreader.utilitaire.Utilitaire;

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
	public void ajouterUnNiveau(StructureDInjectionDeHeader transformation) {
		Builder builder = new Builder();
		
		contenu.stream().forEach(resultat -> builder.ajouter(resultat.stat,
				transformation.creerIncrementateur(resultat.stat, resultat.algo)));
		
		contenant.transformerContenu(builder.build(contenant));
	}

	@Override
	public Stream<Triplet<List<GroupeDeConditions>, Statistique, Algorithme>> recupererAlgo() {
		return contenu.stream().map(r -> new Triplet<>(new ArrayList<>(), r.stat, r.algo));
	}

	@Override
	public void transformerListes(Function<Resultat, ?> classifier, BinaryOperator<Resultat> unifieur) {
		contenu = contenu.stream()
						.collect(Collectors.groupingBy(classifier, Collectors.toList()))
						.values().stream()
						.map(liste -> Utilitaire.fusionnerJusquaStabilite(liste, unifieur))
						.flatMap(liste -> liste.stream())
						.collect(Collectors.toList());
	}
	
	private static class Builder {
		private Map<GroupeDeConditions, List<Resultat>> map = new HashMap<>();

		public Etage build(Contenant contenant) {
			return new Etage(contenant, 
			map.entrySet().stream().map(entry -> new Pair<>(entry.getKey(), new Contenant(entry.getValue())))
				.collect(Pair.toMap()));
		}

		public Builder ajouter(Statistique stat, IncrementateurDeHeader inc) {
			ajouter(inc.getGroupe(), new Resultat(stat, inc.getResultat()));
			return this;
		}

		private void ajouter(GroupeDeConditions groupe, Resultat resultat) {
			Utilitaire.Maps.getX(map, groupe, ArrayList::new).add(resultat);
		}
	}
}
