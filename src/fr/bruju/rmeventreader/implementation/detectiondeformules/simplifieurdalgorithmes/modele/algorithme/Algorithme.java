package fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.algorithme;

import java.util.ArrayList;
import java.util.List;

import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.condition.Condition;

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

	public void ajouterCondition(Condition conditionSeparatrice, Algorithme algorithmeVrai, Algorithme algorithmeFaux) {
		instructions.add(new BlocConditionnel(conditionSeparatrice, algorithmeVrai, algorithmeFaux));
	}

	public void append(ListeurDInstructions sb) {
		for (InstructionGenerale instruction : instructions) {
			instruction.append(sb);
		}
	}

}
