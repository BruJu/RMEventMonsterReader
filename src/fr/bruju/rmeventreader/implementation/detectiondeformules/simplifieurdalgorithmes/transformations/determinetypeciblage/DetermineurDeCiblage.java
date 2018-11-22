package fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.transformations.determinetypeciblage;

import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.transformations.AssignationDeValeurs;
import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.algorithme.Algorithme;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class DetermineurDeCiblage {

	public static ClassificationCible creerClassification(Algorithme algorithme) {
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
