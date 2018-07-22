package fr.bruju.rmeventreader.implementation.formulatracker.contexte.attaques;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import fr.bruju.rmeventreader.implementation.formulatracker.formule.formule.FormuleDeDegats;
import fr.bruju.rmeventreader.implementation.formulatracker.formule.formule.Resultat;

public class Attaque {
	private final String nom;
	private final String cheminFichier;
	private Resultat resultat;

	public Attaque(String nom, String cheminFichier) {
		this.nom = nom;
		this.cheminFichier = cheminFichier;
	}

	public String getNom() {
		return nom;
	}

	public String getChemin() {
		return cheminFichier;
	}

	public void attacherResultat(Resultat resultat) {
		this.resultat = resultat;
	}

	public Resultat getResultat() {
		return resultat;
	}

	public void faireOperation(Function<FormuleDeDegats, List<FormuleDeDegats>> transformation) {
		resultat.map.forEach((stat, liste) -> resultat.map.put(stat,
				liste.stream().flatMap(formule -> transformation.apply(formule).stream()).collect(Collectors.toList()))
		);
	}

}
