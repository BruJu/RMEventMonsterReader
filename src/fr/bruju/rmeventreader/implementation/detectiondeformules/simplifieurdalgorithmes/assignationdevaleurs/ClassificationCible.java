package fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.assignationdevaleurs;

import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.organisation.Classificateur;

public class ClassificationCible implements Classificateur {

	public final Cible cibleChoisie;

	public ClassificationCible(Cible cibleChoisie) {
		this.cibleChoisie = cibleChoisie;
	}

	@Override
	public boolean estUnifiable(Classificateur autre) {
		return autre instanceof ClassificationCible;
	}


	@Override
	public int comparer(Classificateur classificateur) {
		if (!(classificateur instanceof ClassificationCible)) {
			return -1;
		}

		return cibleChoisie.compareTo(((ClassificationCible) classificateur).cibleChoisie);
	}

	public enum Cible {
		Monocible,
		Multicible
	}

	@Override
	public String toString() {
		return cibleChoisie.toString();
	}
}
