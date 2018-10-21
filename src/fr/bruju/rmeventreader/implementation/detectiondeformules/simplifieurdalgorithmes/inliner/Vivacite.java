package fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.inliner;

import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.algorithme.InstructionGenerale;
import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.condition.Condition;

public interface Vivacite {

	public default InstructionGenerale extraireInstructionUnique() {
		return null;
	}

	public static Vivacite combiner(Vivacite v1, Vivacite v2, Condition condition) {
		if (v1 == v2)
			return v1;



		return VivaciteNull.get();
	}


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



	}

}
