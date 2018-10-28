package fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.assignationdevaleurs;

import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.inliner.InlinerGlobal;
import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.algorithme.Algorithme;
import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.expression.ExprVariable;
import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.organisation.AlgorithmeEtiquete;
import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.organisation.Classificateur;
import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.transformation.Separateur;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class Focaliseur implements Separateur {


	@Override
	public void separer(Consumer<AlgorithmeEtiquete> fonctionDAjout, AlgorithmeEtiquete elementASeparer) {
		for (int i = 0 ; i != 3 ; i++) {
			AlgorithmeEtiquete algorithme = cibler(elementASeparer, i);

			if (algorithme != null) {
				fonctionDAjout.accept(algorithme);
			}
		}
	}

	private AlgorithmeEtiquete cibler(AlgorithmeEtiquete elementASeparer, int idMonstre) {
		Algorithme projection = CibleurDeMonstres.projeterSurMonstre(elementASeparer, idMonstre);

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

		return new AlgorithmeEtiquete(elementASeparer, classification, algorithmeResultat);
	}

	public static class ClassificateurMonstreCible implements Classificateur {
		public final int idMonstre;

		public ClassificateurMonstreCible(int idMonstre) {
			this.idMonstre = idMonstre;
		}

		@Override
		public boolean estUnifiable(Classificateur autre) {
			return false;
		}

		@Override
		public int comparer(Classificateur classificateur) {
			return 1;
		}

		@Override
		public String toString() {
			return "Monstre" + idMonstre;
		}
	}
}
