package fr.bruju.rmeventreader.actionmakers.executeur.controlleur;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import fr.bruju.rmeventreader.actionmakers.executeur.handlerInstructions.HandlerInstruction;
import fr.bruju.rmeventreader.actionmakers.executeur.handlerInstructions.HandlerInstructionRetour;
import fr.bruju.rmeventreader.actionmakers.executeur.handlerInstructions.Remplisseur;
import fr.bruju.lcfreader.rmobjets.RMInstruction;

/**
 * Un déchiffreur d'instructions qui permet d'appeler des fonctions ayant des noms plus explicites qu'un code et une
 * liste d'arguments.
 * 
 * @author Bruju
 *
 */
public class DechiffreurInstructions {
	/* =======================================
	 * Liste des instructions connues (static)
	 * ======================================= */
	/** Instructions connues */
	private static Map<Integer, HandlerInstruction> instructionsConnues;
	/** Instructions pouvant renvoyer un booléen connues */
	private static Map<Integer, HandlerInstructionRetour> instructionsConnuesClasse2;
	
	/** Rempli la liste des instructions connues */
	private static void remplirInstructions() {
		if (instructionsConnues != null)
			return;
		
		instructionsConnues = new HashMap<>();
		instructionsConnuesClasse2 = new HashMap<>();
		Stream.of(Remplisseur.getAll()).forEach(remplisseur -> 
			remplisseur.remplirMap(instructionsConnues, instructionsConnuesClasse2)
		);
	}
	
	/* =========
	 * Interface
	 * ========= */
	/** Exécuteur d'instructions associé */
	private ExecuteurInstructions executeur;
	/** Gestion des niveaux de conditions ignorées */
	private Ignorance ignorance;

	/**
	 * Crée un déchiffreur qui utilise l'exécuteur donné
	 * @param executeur L'exécuteur d'instructions
	 */
	public DechiffreurInstructions(ExecuteurInstructions executeur) {
		this.executeur = executeur;
		ignorance = null;
	}
	
	/**
	 * Appelle la méthode de l'exécuteur associé à l'instruction donnée
	 * @param instruction L'instruction à exécuteur
	 */
	public void executer(RMInstruction instruction) {
		remplirInstructions();
		
		Integer code = instruction.code();
		
		if (code == 10) {
			return;
		}
		
		if (ignorance != null) {
			ignorance = ignorance.appliquerCode(code);
			return;
		}
		
		
		HandlerInstruction fonction = instructionsConnues.get(code);
		
		if (fonction != null) {
			fonction.traiter(executeur, instruction.parametres(), instruction.argument());
			return;
		}
		
		HandlerInstructionRetour classe2 = instructionsConnuesClasse2.get(code);
		
		if (classe2 != null) {
			if (!classe2.traiter(executeur, instruction.parametres(), instruction.argument())) {
				ignorance = classe2.ignorer();	
			}
			
			return;
		}
		
		System.out.println(" --> Instruction [" + code + "] non déchiffrable");
	}

	/**
	 * Exécute les instructions données les unes aprés les autres
	 * @param instructions La liste des instructions
	 */
	public void executer(List<RMInstruction> instructions) {
		instructions.forEach(this::executer);
	}
}
