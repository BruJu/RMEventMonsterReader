package fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.inliner;

import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.algorithme.InstructionGenerale;
import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.condition.Condition;

import java.util.HashSet;
import java.util.Set;

public interface Vivacite {

	public default InstructionGenerale extraireInstructionUnique() {
		return null;
	}

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

		
		if (conditions.contains(null)) {
			return VivaciteNull.get();
		}
		
		return new VivaciteConditionnelle(conditions);
	}

	void remplirConditions(Set<Condition> conditions, Condition condition);


	public class VivaciteNull implements Vivacite {
		private static VivaciteNull instance = null;

		public static VivaciteNull get() {
			if (instance == null) {
				instance = new VivaciteNull();
			}

			return instance;
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
	}


	public class VivaciteConditionnelle implements Vivacite {
		private final Set<Condition> conditions;

		public VivaciteConditionnelle(Set<Condition> conditions) {
			this.conditions = conditions;
		}
	}

}
