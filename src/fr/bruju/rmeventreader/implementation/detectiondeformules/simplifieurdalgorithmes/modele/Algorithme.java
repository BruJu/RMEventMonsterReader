package fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele;

import java.util.ArrayList;
import java.util.List;

public class Algorithme {
	private List<InstructionGenerale> instructions = new ArrayList<>();


	public void ajouterInstruction(Instruction instruction) {
		instructions.add(instruction);
	}
	
	public String getString() {
		StringBuilder sb = new StringBuilder();
		
		for (InstructionGenerale instruction : instructions) {
			instruction.append(sb);
			sb.append("\n");
		}
		
		return sb.toString();
	}

	public void ajouterCondition(Condition conditionSeparatrice, Algorithme algorithmeVrai, Algorithme algorithmeFaux) {
		instructions.add(new BlocConditionnel(conditionSeparatrice, algorithmeVrai, algorithmeFaux));
		
	}

}
