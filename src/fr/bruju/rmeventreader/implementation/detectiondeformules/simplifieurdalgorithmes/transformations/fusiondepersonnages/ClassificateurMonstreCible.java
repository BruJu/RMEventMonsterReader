package fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.transformations.fusiondepersonnages;

import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.expression.Statistique;
import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.personnage.Personnage;

public class ClassificateurMonstreCible {
	private final Statistique statistique;

	public ClassificateurMonstreCible(Statistique statistique) {
		this.statistique = statistique;
	}

	@Override
	public String toString() {
		return statistique.getString();
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		ClassificateurMonstreCible that = (ClassificateurMonstreCible) o;
		return statistique.equals(that.statistique);
	}

	@Override
	public int hashCode() {
		return statistique.hashCode();
	}

	public static Integer comparateur(ClassificateurMonstreCible a, ClassificateurMonstreCible b) {
		int z = a.statistique.personnage.getNom().compareTo(b.statistique.personnage.getNom());
		if (z != 0) return z;

		return a.statistique.nom.compareTo(b.statistique.nom);
	}

	public Personnage getPersonnage() {
		return statistique.personnage;
	}

	public Statistique getStatistique() {
		return statistique;
	}
}
