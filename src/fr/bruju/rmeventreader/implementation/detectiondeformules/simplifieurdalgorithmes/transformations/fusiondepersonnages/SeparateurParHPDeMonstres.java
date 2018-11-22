package fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.transformations.fusiondepersonnages;

import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.nouvellestransformations.SeparateurN;
import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.transformations.CibleurDeMonstres;
import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.transformations.inliner.InlinerGlobal;
import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.algorithme.Algorithme;
import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.expression.ExprVariable;
import fr.bruju.util.Pair;

import java.util.ArrayList;
import java.util.List;

/**
 * Transforme l'algorithme pour ne considérer que les dégâts fait à un monstre
 */
public class SeparateurParHPDeMonstres extends SeparateurN {
	public SeparateurParHPDeMonstres() {
		super("Monstre");
	}

	@Override
	protected List<Pair<Algorithme, Object>> diviser(Algorithme algorithme) {
		List<Pair<Algorithme, Object>> liste = new ArrayList<>();

		for (int i = 0 ; i != 3 ; i++) {
			Pair<Algorithme, ClassificateurMonstreCible> paire = instancier(algorithme, i);

			if (paire != null) {
				liste.add(new Pair<>(paire.getLeft(), paire.getRight()));
			}
		}

		return liste;
	}

	private Pair<Algorithme, ClassificateurMonstreCible> instancier(Algorithme algorithme, int idMonstre) {
		Algorithme projection = CibleurDeMonstres.projeterSurMonstre(algorithme, idMonstre);

		int idVariableHPMonstre = 514 + idMonstre;

		if (idMonstre > 0) {
			idVariableHPMonstre++;
		}

		List<ExprVariable> variablesVivantes = new ArrayList<>();
		variablesVivantes.add(new ExprVariable(idVariableHPMonstre));

		InlinerGlobal inliner = new InlinerGlobal(algo -> variablesVivantes);

		Algorithme algorithmeResultat = inliner.simplifier(projection);

		if (algorithmeResultat.estVide()) {
			return null;
		}

		ClassificateurMonstreCible classification = new ClassificateurMonstreCible(idMonstre);

		return new Pair<>(algorithmeResultat, classification);
	}


	public static class ClassificateurMonstreCible {
		public final int idMonstre;

		public ClassificateurMonstreCible(int idMonstre) {
			this.idMonstre = idMonstre;
		}

		@Override
		public String toString() {
			return "Monstre" + (idMonstre + 1);
		}
	}
}
