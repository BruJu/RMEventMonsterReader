package fr.bruju.rmeventreader.implementation.formulatracker.formule;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;


public class Attaque {
	public final String nom;
	
	public Map<ModifStat, List<FormuleDeDegats>> resultat;
	public String chaineAAfficher;
	


	public Attaque(String nom, Map<ModifStat, List<FormuleDeDegats>> resultat) {
		this.nom = nom;
		this.resultat = resultat;
		
	}

	public void faireOperation(Function<FormuleDeDegats, List<FormuleDeDegats>> transformation) {
		resultat.forEach((stat, liste) -> resultat.put(stat,
				liste.stream().flatMap(formule -> transformation.apply(formule).stream()).collect(Collectors.toList()))
		);
	}

	
	public void transformerFormules(UnaryOperator<FormuleDeDegats> fonctionDeTransformation) {
		resultat.replaceAll((k, v) -> v.stream().map(fonctionDeTransformation).collect(Collectors.toList()));		
	}
	
}
