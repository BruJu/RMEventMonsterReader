package fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.personnage;

import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.expression.Statistique;

import java.util.Set;
import java.util.StringJoiner;

public class Groupe extends Personnage {
	private Set<Individu> personnage;
	private String nom;

	public Groupe(String nom) {
		this.nom = nom;
		this.personnage = personnage;
	}


	@Override
	public String getNom() {
		return nom;
	}

	@Override
	public void ajouterPersonnage(Set<Individu> set) {
		set.addAll(personnage);
	}


	public static String definirNom(Set<Individu> personnage) {
		StringJoiner sj = new StringJoiner("/");

		for (Individu individu : personnage) {
			sj.add(individu.getNom());
		}

		return sj.toString();
	}

	public void ajouterNouvellesStatistiquesIssuesDe(Personnage personnage, BaseDePersonnages baseDePersonnages) {
		personnage.forEachStatistique(statistique -> {
			if (this.getStatistique(statistique.nom) == null) {
				int idVariable;

				if (statistique.idVariable < 0) {
					idVariable = baseDePersonnages.getNouvelInterrupteur();
				} else {
					idVariable = baseDePersonnages.getNouvelleVariable();
				}

				ajouterStatistique(new Statistique(idVariable, this, statistique.nom));
			}
		});
	}
}
