package fr.bruju.rmeventreader.implementation.detectiondeformules.transformation.invocation;

import fr.bruju.rmeventreader.implementation.detectiondeformules.modele.algorithme.Algorithme;
import fr.bruju.rmeventreader.implementation.detectiondeformules.transformation.AssignationDeValeurs;
import fr.bruju.rmeventreader.implementation.detectiondeformules.transformation.interfaces.MultiProjecteurDAlgorithme;
import fr.bruju.util.Pair;
import fr.bruju.util.table.Enregistrement;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProjecteurInvocation extends MultiProjecteurDAlgorithme {
	private final Map<Integer, Integer> stockInvoc68;
	private final Map<Integer, Integer> stockInvoc69;

	public ProjecteurInvocation() {
		super("Invocations");

		stockInvoc68 = new HashMap<>();
		stockInvoc68.put(360, 68);

		stockInvoc69 = new HashMap<>();
		stockInvoc69.put(360, 69);
	}

	@Override
	protected List<Pair<Algorithme, Object>> projeter(Enregistrement enregistrement) {
		ArrayList<Pair<Algorithme, Object>> reponse = new ArrayList<>();
		AssignationDeValeurs assignateur = new AssignationDeValeurs();

		Algorithme base = enregistrement.get("Algorithme");
		Algorithme algo68 = assignateur.assigner(base, stockInvoc68);

		if (!assignateur.aFaitUneModification()) {
			reponse.add(new Pair<>(base, 0));
		} else {
			reponse.add(new Pair<>(algo68, 68));
			reponse.add(new Pair<>(assignateur.assigner(base, stockInvoc69), 69));
		}

		return reponse;
	}
}
