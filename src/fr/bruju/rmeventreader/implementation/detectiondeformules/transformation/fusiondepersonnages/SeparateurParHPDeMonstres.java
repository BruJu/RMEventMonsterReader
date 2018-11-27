package fr.bruju.rmeventreader.implementation.detectiondeformules.transformation.fusiondepersonnages;

import fr.bruju.rmeventreader.implementation.detectiondeformules.modele.expression.Statistique;
import fr.bruju.rmeventreader.implementation.detectiondeformules.modele.personnage.BaseDePersonnages;
import fr.bruju.rmeventreader.implementation.detectiondeformules.transformation.interfaces.MultiProjecteurDAlgorithme;
import fr.bruju.rmeventreader.implementation.detectiondeformules.transformation.AssignationDeValeurs;
import fr.bruju.rmeventreader.implementation.detectiondeformules.transformation.inliner.InlineurDAlgorithme;
import fr.bruju.rmeventreader.implementation.detectiondeformules.modele.algorithme.Algorithme;
import fr.bruju.rmeventreader.implementation.detectiondeformules.modele.expression.ExprVariable;
import fr.bruju.util.Pair;
import fr.bruju.util.table.Enregistrement;

import java.util.*;

/**
 * Transforme l'algorithme pour ne considérer que les dégâts fait à un monstre
 */
public class SeparateurParHPDeMonstres extends MultiProjecteurDAlgorithme {
	private final BaseDePersonnages baseDePersonnages;

	public SeparateurParHPDeMonstres(BaseDePersonnages baseDePersonnages) {
		super("Monstre");
		this.baseDePersonnages = baseDePersonnages;
	}


	@Override
	protected List<Pair<Algorithme, Object>> projeter(Enregistrement enregistrement) {
		Algorithme algorithme = enregistrement.get("Algorithme");


		List<Pair<Algorithme, Object>> liste = new ArrayList<>();

		List<ExprVariable> sorties = enregistrement.get("Sorties");
		for (ExprVariable sortie : sorties) {

			if (sortie instanceof Statistique) {
				Pair<Algorithme, ClassificateurMonstreCible> paire = instancier(algorithme, (Statistique) sortie);

				if (paire != null) {
					liste.add(new Pair<>(paire.getLeft(), paire.getRight()));
				}
			}
		}

		return liste;
	}

	public static Algorithme projeterSurMonstre(Algorithme algorithme, int idMonstre) {
		Map<Integer, Integer> variables = new HashMap<>();

		variables.put(436, 5 + idMonstre);
		variables.put(42, 70 + idMonstre);

		AssignationDeValeurs assignateur = new AssignationDeValeurs();
		return assignateur.assigner(algorithme, variables);
	}

	private Pair<Algorithme, ClassificateurMonstreCible> instancier(Algorithme algorithme, Statistique statistique) {
		Algorithme projection;

		if (statistique.personnage.getNom().startsWith("Monstre")) {
			projection = projeterSurMonstre(algorithme, statistique.personnage.getNom().charAt(7) - '1');
		} else {
			projection = projeterSurMonstre(algorithme, 0);
		}


		List<ExprVariable> variablesVivantes = new ArrayList<>();
		variablesVivantes.add(statistique);

		Algorithme algorithmeResultat = InlineurDAlgorithme.simplifier(variablesVivantes, projection);

		if (algorithmeResultat.estVide()) {
			return null;
		}

		ClassificateurMonstreCible classification = new ClassificateurMonstreCible(statistique);

		return new Pair<>(algorithmeResultat, classification);
	}
}
