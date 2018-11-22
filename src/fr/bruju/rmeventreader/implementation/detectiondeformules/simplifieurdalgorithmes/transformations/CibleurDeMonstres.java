package fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.transformations;

import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.transformations.AssignationDeValeurs;
import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.algorithme.Algorithme;

import java.util.HashMap;
import java.util.Map;

public class CibleurDeMonstres {

	public static Algorithme projeterSurMonstre(Algorithme algorithme, int idMonstre) {
		Map<Integer, Integer> variables = new HashMap<>();

		variables.put(436, 5 + idMonstre);
		variables.put(42, 70 + idMonstre);

		AssignationDeValeurs assignateur = new AssignationDeValeurs();
		return assignateur.assigner(algorithme, variables);
	}
}
