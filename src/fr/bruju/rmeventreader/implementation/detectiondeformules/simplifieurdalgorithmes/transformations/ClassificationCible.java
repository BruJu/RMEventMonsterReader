package fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.transformations;

import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.algorithme.Algorithme;
import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.nouvellestransformations.AjouteurDeTag;
import fr.bruju.util.table.Enregistrement;

import java.util.HashMap;
import java.util.Map;

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

	public static class Determineur extends AjouteurDeTag {
		public Determineur() {
			super("Ciblage");
		}

		@Override
		protected Object genererNouveauChamp(Enregistrement enregistrement) {
			Algorithme algorithme = enregistrement.get("Algorithme");

			Map<Integer, Integer> variables = new HashMap<>();

			variables.put(436, 5);
			variables.put(42, 70);

			AssignationDeValeurs assignateur = new AssignationDeValeurs();
			Algorithme algorithmeCiblantMonstre0 = assignateur.assigner(algorithme, variables);

			ClassificationCible.Cible classificationCible;

			if (algorithme.estIdentique(algorithmeCiblantMonstre0)) {
				classificationCible = ClassificationCible.Cible.Multicible;
			} else {
				classificationCible = ClassificationCible.Cible.Monocible;
			}

			ClassificationCible classification = new ClassificationCible(classificationCible);

			return classification;
		}
	}
}
