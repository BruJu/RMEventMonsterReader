package fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.inliner;

import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.algorithme.InstructionGenerale;

public interface Vivacite {


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
	}


	public class VivaciteConditionnelle implements Vivacite {



	}

}
