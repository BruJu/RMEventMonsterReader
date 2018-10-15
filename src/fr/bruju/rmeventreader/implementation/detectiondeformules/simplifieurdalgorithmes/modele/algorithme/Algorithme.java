package fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.algorithme;

import java.util.ArrayList;
import java.util.List;

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
		algorithme.instructions.forEach(instructions::add);
	}

	public int nombreDInstructions() {
		return instructions.size();
	}
}
