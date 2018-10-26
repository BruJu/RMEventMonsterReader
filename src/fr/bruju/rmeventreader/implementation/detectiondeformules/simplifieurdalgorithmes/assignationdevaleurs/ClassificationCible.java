package fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.assignationdevaleurs;

import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.organisation.Classificateur;

public class ClassificationCible implements Classificateur {

	private final Cible cibleChoisie;

	public ClassificationCible(Cible cibleChoisie) {
		this.cibleChoisie = cibleChoisie;
	}

	@Override
	public boolean estUnifiable(Classificateur autre) {
		return autre instanceof ClassificationCible;
	}

	@Override
	public Classificateur ajouter(Classificateur classificateur) {
		return new ClassificationCible(Cible.Plusieurs);
	}

	@Override
	public int comparer(Classificateur classificateur) {
		if (!(classificateur instanceof ClassificationCible)) {
			return -1;
		}

		return cibleChoisie.compareTo(((ClassificationCible) classificateur).cibleChoisie);
	}

	public enum Cible {
		Ennemi1,
		Ennemi2,
		Ennemi3,
		Plusieurs
	}
}
