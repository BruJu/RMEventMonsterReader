package fr.bruju.rmeventreader.implementation.formulatracker.formule;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;

import fr.bruju.rmeventreader.implementation.formulatracker.formule.personnage.Statistique;

public class Resultat {
	public Map<Statistique, List<FormuleDeDegats>> map;
	
	public Resultat() {
		map = new HashMap<>();
	}

	public Resultat integrer(Statistique stat, List<FormuleDeDegats> formules) {
		map.put(stat, formules);
		return this;
	}
	
	public void transformerFormules(UnaryOperator<FormuleDeDegats> fonctionDeTransformation) {
		map.replaceAll((k, v) -> v.stream().map(fonctionDeTransformation).collect(Collectors.toList()));		
	}
	
}
