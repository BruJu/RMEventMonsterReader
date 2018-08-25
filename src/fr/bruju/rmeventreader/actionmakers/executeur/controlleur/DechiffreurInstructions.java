package fr.bruju.rmeventreader.actionmakers.executeur.controlleur;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import fr.bruju.rmeventreader.actionmakers.executeur.handlerInstructions.HandlerInstruction;
import fr.bruju.rmeventreader.actionmakers.executeur.handlerInstructions.Remplisseur;
import fr.bruju.rmeventreader.dictionnaires.header.Instruction;

public class DechiffreurInstructions {
	private static Map<Integer, HandlerInstruction> instructionsConnues;
	
	private ExecuteurInstructions executeur;

	public DechiffreurInstructions(ExecuteurInstructions executeur) {
		this.executeur = executeur;
	}
	
	public void executer(Instruction instruction) {
		remplirInstructions();
		
		Integer code = instruction.code;
		
		HandlerInstruction fonction = instructionsConnues.get(code);
		
		if (fonction == null) {
			System.out.println(" --> Instruction [" + instruction.toString(true) + "] non déchiffrable");
		} else {
			fonction.traiter(executeur, instruction.parameters, instruction.string);
		}
	}
	
	private static void remplirInstructions() {
		if (instructionsConnues != null)
			return;
		
		instructionsConnues = new HashMap<>();
		Stream.of(Remplisseur.getAll()).forEach(remplisseur -> remplisseur.remplirMap(instructionsConnues));
	}

	public void executer(List<Instruction> instructions) {
		instructions.forEach(this::executer);
	}
	
	
	
	
}
