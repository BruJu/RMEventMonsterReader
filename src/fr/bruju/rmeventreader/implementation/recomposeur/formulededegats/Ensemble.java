package fr.bruju.rmeventreader.implementation.recomposeur.formulededegats;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;

import fr.bruju.rmeventreader.actionmakers.composition.composant.valeur.Algorithme;
import fr.bruju.rmeventreader.implementation.recomposeur.exploitation.BaseDeVariables;
import fr.bruju.rmeventreader.utilitaire.Pair;

public class Ensemble {
	private BaseDeVariables base;
	private Map<Header, Algorithme> algorithmesTrouves;

	public Ensemble(Map<Header, Map<Integer, Algorithme>> map, BaseDeVariables base) {
		this.base = base;
		this.algorithmesTrouves = new HashMap<>();

		map.forEach((head, body) -> body.forEach((variable, algorithme) -> this.algorithmesTrouves
				.put(new Header(head, base.getStatistiqueById(variable)), algorithme)));
	}
	
	
	public void modifierAlgorithmes(UnaryOperator<Algorithme> fonctionDeTransformation) {
		algorithmesTrouves.replaceAll((header, body) -> fonctionDeTransformation.apply(body));
	}
	
	public void integrationDansHeader(BiFunction<Header, Algorithme, Pair<Header, Algorithme>> fonction) {
		algorithmesTrouves = algorithmesTrouves.entrySet()
						  		.stream()
						  		.map(entrySet -> fonction.apply(entrySet.getKey(), entrySet.getValue()))
						  		.collect(Collectors.toMap(Pair::getLeft, Pair::getRight));
	}
	
	public void unifier() {
		base = base;
	}
	
	
}
