package fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.algorithme;

import fr.bruju.rmeventreader.utilitaire.Utilitaire;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

public class Algorithme {
	private final List<InstructionGenerale> instructions = new ArrayList<>();

	public void ajouterInstruction(InstructionGenerale instruction) {
		instructions.add(instruction);
	}

	public boolean estVide() {
		for (InstructionGenerale instruction : instructions) {
			if (!instruction.estVide())
				return false;
		}
		
		return true;
	}
	
	public String getString() {
		ListeurDInstructions sb = new ListeurDInstructions();
		append(sb);
		return sb.toString();
	}


	public void append(ListeurDInstructions sb) {
		for (InstructionGenerale instruction : instructions) {
			instruction.append(sb);
		}
	}

	public void accept(VisiteurDAlgorithme visiteurDAlgorithme) {
		for (InstructionGenerale instruction : instructions) {
			instruction.accept(visiteurDAlgorithme);
		}
	}
	
	public void acceptInverse(VisiteurDAlgorithme visiteurDAlgorithme) {
		for (int i = instructions.size() - 1 ; i >= 0 ; i--) {
			instructions.get(i).accept(visiteurDAlgorithme);
		}
	}

	public int nombreDInstructions() {
		return instructions.size();
	}

	public boolean estIdentique(Algorithme algorithme) {
		Supplier<InstructionGenerale> itThis = new IterateurEffectif();
		Supplier<InstructionGenerale> itAutre = algorithme.new IterateurEffectif();
		return Utilitaire.comparerIterateursBoolean(itThis, itAutre, InstructionGenerale::estIdentique);
	}

	private class IterateurEffectif implements Supplier<InstructionGenerale> {
		private int i = -1;

		public InstructionGenerale get() {
			do {
				i++;
			} while(estUnAffichage());

			return extraire();
		}

		private boolean estUnAffichage() {
			return i < instructions.size() && instructions.get(i) instanceof InstructionAffichage;
		}

		private InstructionGenerale extraire() {
			return i >= instructions.size() ? null : instructions.get(i);
		}
	}

}
