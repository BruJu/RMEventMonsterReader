package fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.algorithme;

import com.sun.org.apache.bcel.internal.generic.Instruction;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

public class Algorithme {
	private List<InstructionGenerale> instructions = new ArrayList<>();

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
		instructions.forEach(visiteurDAlgorithme::visit);
	}
	
	public void acceptInverse(VisiteurDAlgorithme visiteurDAlgorithme) {
		for (int i = instructions.size() - 1 ; i >= 0 ; i--) {
			instructions.get(i).accept(visiteurDAlgorithme);
		}
	}

	public void ajouter(Algorithme algorithme) {
		instructions.addAll(algorithme.instructions);
	}

	public int nombreDInstructions() {
		return instructions.size();
	}

	public boolean estIdentique(Algorithme algorithme) {
		Iterator<InstructionGenerale> itThis = getIterateurEffectif();
		Iterator<InstructionGenerale> itAutre = algorithme.getIterateurEffectif();

		while (true) {

			if (!itThis.hasNext() || !itAutre.hasNext()) {
				return !itThis.hasNext() && !itAutre.hasNext();
			}

			InstructionGenerale instructionThis = itThis.next();
			InstructionGenerale instructionAutre = itAutre.next();

			if (!instructionThis.estIdentique(instructionAutre)) {
				return false;
			}
		}
	}

	private Iterator<InstructionGenerale> getIterateurEffectif() {
		return new IterateurInstructionEffectives();
	}

	private class IterateurInstructionEffectives implements Iterator<InstructionGenerale> {
		private int i = -1;
		private InstructionGenerale instructionActuelle;

		public IterateurInstructionEffectives() {
			next();
			instructionActuelle = next();
		}

		@Override
		public boolean hasNext() {
			return i != instructions.size();
		}

		@Override
		public InstructionGenerale next() {
			InstructionGenerale instructionPrecedente = instructionActuelle;

			while (true) {
				i++;

				if (i == instructions.size()) {
					return instructionPrecedente;
				}

				instructionActuelle = instructions.get(i);

				if (!(instructionActuelle instanceof InstructionAffichage)) {
					return instructionPrecedente;
				}
			}
		}
	}
}
