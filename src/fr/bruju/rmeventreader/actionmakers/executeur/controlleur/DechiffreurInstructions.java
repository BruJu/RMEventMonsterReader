package fr.bruju.rmeventreader.actionmakers.executeur.controlleur;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import fr.bruju.rmeventreader.actionmakers.executeur.handlerInstructions.HandlerInstruction;
import fr.bruju.rmeventreader.actionmakers.executeur.handlerInstructions.HandlerInstructionRetour;
import fr.bruju.rmeventreader.actionmakers.executeur.handlerInstructions.Remplisseur;
import fr.bruju.rmeventreader.dictionnaires.header.Instruction;

public class DechiffreurInstructions {
	private static Map<Integer, HandlerInstruction> instructionsConnues;
	private static Map<Integer, HandlerInstructionRetour> instructionsConnuesClasse2;
	
	private ExecuteurInstructions executeur;
	private Ignorance ignorance;

	public DechiffreurInstructions(ExecuteurInstructions executeur) {
		this.executeur = executeur;
		ignorance = null;
	}
	
	public void executer(Instruction instruction) {
		remplirInstructions();
		
		Integer code = instruction.code;
		
		if (ignorance != null) {
			ignorance = ignorance.appliquerCode(code);
			return;
		}
		
		
		HandlerInstruction fonction = instructionsConnues.get(code);
		
		if (fonction != null) {
			fonction.traiter(executeur, instruction.parameters, instruction.string);
			return;
		}
		
		HandlerInstructionRetour classe2 = instructionsConnuesClasse2.get(code);
		
		if (classe2 != null) {
			if (!classe2.traiter(executeur, instruction.parameters, instruction.string)) {
				ignorance = classe2.ignorer();	
			}
			
			return;
		}
		
		System.out.println(" --> Instruction [" + instruction.toString(true) + "] non déchiffrable");
	}
	
	private static void remplirInstructions() {
		if (instructionsConnues != null)
			return;
		
		instructionsConnues = new HashMap<>();
		instructionsConnuesClasse2 = new HashMap<>();
		Stream.of(Remplisseur.getAll()).forEach(remplisseur -> 
			remplisseur.remplirMap(instructionsConnues, instructionsConnuesClasse2)
		);
	}

	public void executer(List<Instruction> instructions) {
		instructions.forEach(this::executer);
	}
	
	
	
	
}
