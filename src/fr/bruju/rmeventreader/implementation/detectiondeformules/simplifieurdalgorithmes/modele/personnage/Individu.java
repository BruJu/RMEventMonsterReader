package fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.personnage;

import java.util.Set;

public class Individu implements Personnage {
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
}
