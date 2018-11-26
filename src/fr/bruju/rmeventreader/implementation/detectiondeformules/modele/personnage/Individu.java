package fr.bruju.rmeventreader.implementation.detectiondeformules.modele.personnage;

import java.util.Set;

public class Individu extends Personnage implements Comparable<Individu> {
	public final String nom;

	public Individu(String nom) {
		this.nom = nom;
	}

	@Override
	public String getNom() {
		return nom;
	}

	@Override
	public void ajouterPersonnage(Set<Individu> set) {
		set.add(this);
	}

	@Override
	public int compareTo(Individu o) {
		return nom.compareTo(o.nom);
	}
}
