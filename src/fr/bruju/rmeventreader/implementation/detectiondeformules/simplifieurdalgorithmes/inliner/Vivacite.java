package fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.inliner;

import fr.bruju.rmdechiffreur.modele.Comparateur;
import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.algorithme.InstructionGenerale;
import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.condition.Condition;
import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.condition.ConditionVariable;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public interface Vivacite {

	public default InstructionGenerale extraireInstructionUnique() {
		return null;
	}

	public Vivacite absorber(Condition condition);

	public static Vivacite combiner(Vivacite v1, Vivacite v2, Condition condition) {
		if (v1 == v2)
			return v1;

		Set<Condition> conditions = new HashSet<>();
		
		if (v1 != null) {
			v1.remplirConditions(conditions, condition);
		}
		
		if (v2 != null) {
			v2.remplirConditions(conditions, condition.inverser());
		}

		trouverLesConditionsInversee(conditions);

		if (conditions.contains(null)) {
			return VivaciteNull.get();
		}
		
		return new VivaciteConditionnelle(conditions);
	}

	static void trouverLesConditionsInversee(Set<Condition> conditions) {
		for (Condition condition : conditions) {
			Condition inverse = condition.inverser();

			if (conditions.contains(inverse)) {
				conditions.add(null);
				return;
			}
		}
	}

	public void remplirConditions(Set<Condition> conditions, Condition condition);


	public class VivaciteNull implements Vivacite {
		private static VivaciteNull instance = null;

		public static VivaciteNull get() {
			if (instance == null) {
				instance = new VivaciteNull();
			}

			return instance;
		}

		@Override
		public Vivacite absorber(Condition condition) {
			return this;
		}

		@Override
		public void remplirConditions(Set<Condition> conditions, Condition condition) {
			conditions.add(condition);
		}
	}


	public class AffectationUnique implements Vivacite {
		public final InstructionGenerale instruction;

		public AffectationUnique(InstructionGenerale instruction) {
			this.instruction = instruction;
		}

		@Override
		public InstructionGenerale extraireInstructionUnique() {
			return instruction;
		}

		@Override
		public Vivacite absorber(Condition condition) {
			return this;
		}

		@Override
		public void remplirConditions(Set<Condition> conditions, Condition condition) {
			conditions.add(condition);
		}
	}


	public class VivaciteConditionnelle implements Vivacite {
		private final Set<Condition> conditions;

		public VivaciteConditionnelle(Set<Condition> conditions) {
			this.conditions = conditions;
		}

		@Override
		public void remplirConditions(Set<Condition> conditionsARemplir, Condition conditionExploree) {

			Set<Condition> conditionsARemplirIntermediaire = new HashSet<>();
			for (Condition conditionConnue : this.conditions) {
				conditionsARemplirIntermediaire.add(intersectionLache(conditionExploree, conditionConnue));
			}

			if (conditionsARemplirIntermediaire.contains(conditionExploree)) {
				conditionsARemplir.add(conditionExploree);
			} else {
				conditionsARemplir.addAll(conditionsARemplir);
			}
		}
		
		@Override
		public Vivacite absorber(Condition condition) {
			if (!(condition instanceof ConditionVariable)) {
				return this;
			}
			
			ConditionVariable cv = (ConditionVariable) condition;
			
			if (cv.comparateur != Comparateur.IDENTIQUE) {
				return this;
			}
			
			Set<Condition> nouvellesConditions = new HashSet<>();

			for (Condition conditionPresente : conditions) {
				Condition conditionAbsorbee = absorberCondition(conditionPresente, cv);

				if (conditionAbsorbee != null) {
					nouvellesConditions.add(conditionAbsorbee);
				}
			}
			
			return new VivaciteConditionnelle(nouvellesConditions);
		}

		private static Condition absorberCondition(Condition conditionPresente, ConditionVariable cv) {
			if (!(conditionPresente instanceof ConditionVariable)) {
				return conditionPresente;
			}

			// TODO


			return conditionPresente;
		}

		/**
		 * L'intersection lache N est définie tel que : A N B = A sauf si B est inclus dans A où dans ce cas A N B = B.
		 * <br>L'implémentation actuelle est simpliste, ne prenant que les = et !=
		 */
		private Condition intersectionLache(Condition conditionExploree, Condition conditionConnue) {
			if (!(conditionExploree instanceof ConditionVariable) || !(conditionConnue instanceof ConditionVariable)) {
				return conditionExploree;
			}

			ConditionVariable cAbsorbant = (ConditionVariable) conditionExploree;
			ConditionVariable cAbsorbe = (ConditionVariable) conditionConnue;

			if (!cAbsorbant.gauche.equals(cAbsorbe.gauche)) {
				return conditionExploree;
			}

			if (cAbsorbant.comparateur != Comparateur.DIFFERENT && cAbsorbant.comparateur != Comparateur.IDENTIQUE) {
				return conditionExploree;
			}

			Integer evaluationDroiteAbsorbant = cAbsorbant.droite.evaluer();
			Integer evaluationDroiteAbsorbe = cAbsorbant.droite.evaluer();

			if (evaluationDroiteAbsorbant == null || evaluationDroiteAbsorbe == null
					|| evaluationDroiteAbsorbant.equals(evaluationDroiteAbsorbe)) {
				return conditionExploree;
			}

			return cAbsorbe;
		}
	}

}
