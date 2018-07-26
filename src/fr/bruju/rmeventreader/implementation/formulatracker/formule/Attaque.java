package fr.bruju.rmeventreader.implementation.formulatracker.formule;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Attaque {
	public final String nom;
	
	public final Resultat resultat;
	public String chaineAAfficher;
	


	public Attaque(String nom, Resultat resultat) {
		this.nom = nom;
		this.resultat = resultat;
		
	}

	public void faireOperation(Function<FormuleDeDegats, List<FormuleDeDegats>> transformation) {
		resultat.map.forEach((stat, liste) -> resultat.map.put(stat,
				liste.stream().flatMap(formule -> transformation.apply(formule).stream()).collect(Collectors.toList()))
		);
	}

}
