package fr.bruju.rmeventreader.actionmakers.controlleur;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import fr.bruju.lcfreader.rmobjets.RMInstruction;
import fr.bruju.rmeventreader.actionmakers.handlerInstructions.Traiteur;
import fr.bruju.rmeventreader.actionmakers.handlerInstructions.DechiffrageDesInstructions;

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
	
	/** Instructions dont le décryptage est connu */
	private static Map<Integer, Traiteur> instructionsConnues;
	
	/**
	 * Rempli la liste des instructions connues. Fonction static pour ne pas recréer tous les traiteurs à chaque
	 * nouveau déchiffrage
	 */
	private static void remplirInstructions() {
		if (instructionsConnues == null) {
			instructionsConnues = new DechiffrageDesInstructions().getTraiteurs();
		}
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
		remplirInstructions();
		this.executeur = executeur;
	}
	
	/**
	 * Appelle la méthode de l'exécuteur associé à l'instruction donnée
	 * @param instruction L'instruction à exécuteur
	 */
	public void executer(RMInstruction instruction) {
		int code = instruction.code();
		
		if (ignorance != null) {
			ignorance = ignorance.appliquerCode(code);
		} else {
			Traiteur traiteur = instructionsConnues.get(code);
		
			if (traiteur == null) {
				System.out.println(" --> Instruction [" + code + "] non déchiffrable "
						+ "<" + instruction.argument() + "> "
						+ Arrays.toString(instruction.parametres()));
			} else {
				if (!traiteur.traiter(executeur, instruction.parametres(), instruction.argument())) {
					ignorance = traiteur.creerIgnorance();	
				}
			}
		}
	}

	/**
	 * Exécute les instructions données les unes aprés les autres
	 * @param instructions La liste des instructions
	 */
	public void executer(List<RMInstruction> instructions) {
		instructions.forEach(this::executer);
	}
}
