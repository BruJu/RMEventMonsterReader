package fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.transformations.fusiondepersonnages;

import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.expression.Statistique;
import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.personnage.BaseDePersonnages;
import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.personnage.Personnage;
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
	private final BaseDePersonnages baseDePersonnages;

	public SeparateurParHPDeMonstres(BaseDePersonnages baseDePersonnages) {
		super("Monstre");
		this.baseDePersonnages = baseDePersonnages;
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

		Personnage personnage = baseDePersonnages.getPersonnage("Monstre" + (idMonstre+1));
		Statistique statistiqueHP = personnage.getStatistique("HP");

		List<ExprVariable> variablesVivantes = new ArrayList<>();
		variablesVivantes.add(statistiqueHP);

		InlinerGlobal inliner = new InlinerGlobal(algo -> variablesVivantes);

		Algorithme algorithmeResultat = inliner.simplifier(projection);

		if (algorithmeResultat.estVide()) {
			return null;
		}

		int bitMonstre = 1 << (idMonstre);

		ClassificateurMonstreCible classification = new ClassificateurMonstreCible(personnage);

		return new Pair<>(algorithmeResultat, classification);
	}


	public static class ClassificateurMonstreCible {
		private final Personnage personnage;

		public ClassificateurMonstreCible(Personnage personnage) {
			this.personnage = personnage;
		}

		@Override
		public String toString() {
			return personnage.getNom() + ".HP";
		}

		@Override
		public boolean equals(Object o) {
			if (this == o) return true;
			if (o == null || getClass() != o.getClass()) return false;
			ClassificateurMonstreCible that = (ClassificateurMonstreCible) o;
			return personnage.equals(that.personnage);
		}

		@Override
		public int hashCode() {
			return personnage.hashCode();
		}

		public static Integer comparateur(ClassificateurMonstreCible a, ClassificateurMonstreCible b) {
			return a.personnage.getNom().compareTo(b.personnage.getNom());
		}

		public Personnage getPersonnage() {
			return personnage;
		}
	}
}
