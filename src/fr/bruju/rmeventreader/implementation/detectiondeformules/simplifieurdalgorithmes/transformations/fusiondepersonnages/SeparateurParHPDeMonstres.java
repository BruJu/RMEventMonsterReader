package fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.transformations.fusiondepersonnages;

import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.nouvellestransformations.MultiProjecteurDAlgorithme;
import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.transformations.AssignationDeValeurs;
import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.transformations.inliner.InlinerGlobal;
import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.algorithme.Algorithme;
import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.expression.ExprVariable;
import fr.bruju.util.Pair;

import java.util.*;

/**
 * Transforme l'algorithme pour ne considérer que les dégâts fait à un monstre
 */
public class SeparateurParHPDeMonstres extends MultiProjecteurDAlgorithme {
	public SeparateurParHPDeMonstres() {
		super("Monstre");
	}

	@Override
	protected List<Pair<Algorithme, Object>> projeter(Algorithme algorithme) {
		List<Pair<Algorithme, Object>> liste = new ArrayList<>();

		for (int i = 0 ; i != 3 ; i++) {
			Pair<Algorithme, ClassificateurMonstreCible> paire = instancier(algorithme, i);

			if (paire != null) {
				liste.add(new Pair<>(paire.getLeft(), paire.getRight()));
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

	private Pair<Algorithme, ClassificateurMonstreCible> instancier(Algorithme algorithme, int idMonstre) {
		Algorithme projection = projeterSurMonstre(algorithme, idMonstre);

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

		int bitMonstre = 1 << (idMonstre);

		ClassificateurMonstreCible classification = new ClassificateurMonstreCible(bitMonstre);

		return new Pair<>(algorithmeResultat, classification);
	}


	public static class ClassificateurMonstreCible {
		public final int idMonstre;

		public ClassificateurMonstreCible(int idMonstre) {
			this.idMonstre = idMonstre;
		}

		public ClassificateurMonstreCible(ClassificateurMonstreCible monstre1, ClassificateurMonstreCible monstre2) {
			this.idMonstre = monstre1.idMonstre + monstre2.idMonstre;
		}

		@Override
		public String toString() {
			StringBuilder sb = new StringBuilder().append("Monstre");

			if ((idMonstre & 0x01) == 0x01) {
				sb.append("1");
			}
			if ((idMonstre & 0x02) == 0x02) {
				sb.append("2");
			}
			if ((idMonstre & 0x04) == 0x04) {
				sb.append("3");
			}

			return sb.toString();
		}

		@Override
		public boolean equals(Object o) {
			if (this == o) return true;
			if (o == null || getClass() != o.getClass()) return false;
			ClassificateurMonstreCible that = (ClassificateurMonstreCible) o;
			return idMonstre == that.idMonstre;
		}

		@Override
		public int hashCode() {
			return Objects.hash(idMonstre);
		}
	}
}
