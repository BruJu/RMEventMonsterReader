package fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.algorithme;

import com.sun.org.apache.bcel.internal.generic.Instruction;

import java.util.ArrayList;
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
		int iThis = -1;
		int iAutre = -1;

		while (true) {
			iThis = prochaineInstruction(iThis);
			iAutre = algorithme.prochaineInstruction(iAutre);

			if (iThis == -1 || iAutre == -1) {
				return iThis == iAutre;
			}

			InstructionGenerale instructionThis = instructions.get(iThis);
			InstructionGenerale instructionAutre = algorithme.instructions.get(iAutre);

			if (!instructionThis.estIdentique(instructionAutre)) {
				return false;
			}
		}
	}

	private int prochaineInstruction(int i) {
		do {
			i++;
		} while(i != instructions.size() && instructions.get(i) instanceof InstructionAffectation);

		return (i == instructions.size()) ? -1 : i;
	}

}
