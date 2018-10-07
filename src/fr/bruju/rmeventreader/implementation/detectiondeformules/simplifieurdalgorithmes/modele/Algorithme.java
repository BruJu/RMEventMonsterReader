package fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele;

import java.util.ArrayList;
import java.util.List;

public class Algorithme {
	private List<Instruction> instructions = new ArrayList<>();
	
	
	public void ajouterInstruction(Instruction instruction) {
		instructions.add(instruction);
	}
	
	
	public String getString() {
		StringBuilder sb = new StringBuilder();
		
		for (Instruction instruction : instructions) {
			instruction.append(sb);
			sb.append("\n");
		}
		
		return sb.toString();
	}
}
