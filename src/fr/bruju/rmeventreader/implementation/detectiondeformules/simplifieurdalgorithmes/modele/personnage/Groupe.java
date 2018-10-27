package fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.personnage;

import java.util.Set;
import java.util.StringJoiner;

public class Groupe implements Personnage {
	private Set<Individu> personnage;
	private String nom;

	public Groupe(Set<Individu> personnage) {
		this.personnage = personnage;
		definirNom();
	}

	private void definirNom() {
		StringJoiner sj = new StringJoiner("/");

		for (Individu individu : personnage) {
			sj.add(individu.getNom());
		}

		nom = sj.toString();
	}

	@Override
	public String getNom() {
		return nom;
	}

	@Override
	public void ajouterPersonnage(Set<Individu> set) {
		set.addAll(personnage);
	}
}
