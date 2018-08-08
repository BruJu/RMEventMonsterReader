package fr.bruju.rmeventreader.implementation.recomposeur.formulededegats;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.BinaryOperator;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;

import fr.bruju.rmeventreader.actionmakers.composition.composant.valeur.Algorithme;
import fr.bruju.rmeventreader.actionmakers.composition.visiteur.template.VisiteurConstructeur;
import fr.bruju.rmeventreader.implementation.recomposeur.exploitation.BaseDeVariables;
import fr.bruju.rmeventreader.utilitaire.Pair;
import fr.bruju.rmeventreader.utilitaire.Utilitaire;
import fr.bruju.util.similaire.CollectorBySimilarity;

public class Ensemble {
	private Map<Header, Algorithme> algorithmesTrouves;

	public Ensemble(Map<Header, Map<Integer, Algorithme>> map, BaseDeVariables base) {
		this.algorithmesTrouves = new HashMap<>();

		map.forEach((head, body) -> body.forEach((variable, algorithme) -> this.algorithmesTrouves
				.put(new Header(head, base.getStatistiqueById(variable)), algorithme)));
	}
	
	public Map<Header, Algorithme> getMap() {
		return this.algorithmesTrouves;
	}

	public Ensemble modifierAlgorithmes(UnaryOperator<Algorithme> fonctionDeTransformation) {
		algorithmesTrouves.replaceAll((header, body) -> fonctionDeTransformation.apply(body));
		
		return this;
	}

	public Ensemble integrationDansHeader(BiFunction<Header, Algorithme, Pair<Header, Algorithme>> fonction) {
		algorithmesTrouves = algorithmesTrouves.entrySet().stream()
				.map(entrySet -> fonction.apply(entrySet.getKey(), entrySet.getValue()))
				.collect(Collectors.toMap(Pair::getLeft, Pair::getRight));
		
		return this;
	}

	public Ensemble unifier(BinaryOperator<Pair<Header, Algorithme>> fonctionFusion) {
		CollectorBySimilarity<Pair<Header, Algorithme>> collector = new CollectorBySimilarity<>(
				p -> p.getLeft().hashUnifiable(), (a, b) -> a.getLeft().estUnifiable(b.getLeft()));

		Collection<List<Pair<Header, Algorithme>>> fusionnables = algorithmesTrouves.entrySet().stream()
				.map(entrySet -> new Pair<>(entrySet.getKey(), entrySet.getValue())).collect(collector).getMap()
				.values();

		algorithmesTrouves = fusionnables.stream()
				.map(liste -> Utilitaire.fusionnerJusquaStabilite(liste, fonctionFusion))
				.flatMap(liste -> liste.stream())
				.collect(Collectors.toMap(paire -> paire.getLeft(), paire -> paire.getRight()));
		
		return this;
	}

	public Ensemble reconstruire(VisiteurConstructeur constructeur) {
		modifierAlgorithmes(algo -> (Algorithme) constructeur.traiter(algo));
		return this;
	}


}
