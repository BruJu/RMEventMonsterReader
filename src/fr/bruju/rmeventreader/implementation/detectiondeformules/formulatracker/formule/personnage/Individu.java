package fr.bruju.rmeventreader.implementation.detectiondeformules.formulatracker.formule.personnage;

import java.util.Set;
import java.util.TreeSet;
import java.util.function.Function;


public class Individu<T extends VariablesAssociees> implements Personne<T>, Comparable<Personne<T>> {
	private final String nom;
	private final T variablesAssociees;

	public Individu(String nom, Function<Individu<T>, T> variablesAssociees) {
		this.nom = nom;
		this.variablesAssociees = variablesAssociees.apply(this);
	}

	@Override
	public String getNom() {
		return nom;
	}

	@Override
	public T getVariablesAssociees() {
		return variablesAssociees;
	}

	@Override
	public Set<Individu<T>> getIndividus() {
		Set<Individu<T>> set = new TreeSet<>();
		set.add(this);
		return set;
	}

	@Override
	public int compareTo(Personne<T> arg0) {
		return nom.compareTo(arg0.getNom());
	}
}
