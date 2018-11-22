package fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.transformations.determinetypeciblage;

public class ClassificationCible {

	public final Cible cibleChoisie;

	public ClassificationCible(Cible cibleChoisie) {
		this.cibleChoisie = cibleChoisie;
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
