package fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.transformations.determinetypeciblage;

import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.transformations.AssignationDeValeurs;
import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.algorithme.Algorithme;
import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.organisation.AlgorithmeEtiquete;
import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.transformation.Separateur;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class DetermineurDeCiblage implements Separateur {
	@Override
	public void separer(Consumer<AlgorithmeEtiquete> fonctionDAjout, AlgorithmeEtiquete elementASeparer) {
		Algorithme algorithme = elementASeparer.getAlgorithme();
		ClassificationCible classification = creerClassification(algorithme);

		AlgorithmeEtiquete algorithmeEtiquete = new AlgorithmeEtiquete(elementASeparer, classification, algorithme);
		fonctionDAjout.accept(algorithmeEtiquete);
	}


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
