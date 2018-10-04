package fr.bruju.rmeventreader.actionmakers.controlleur;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

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
	
	/** Booléen qui permet de savoir si le programme doit afficher les instructions avec erreur ou non */
	public static boolean AFFICHER_ERREURS = false;
	
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
	
	/**
	 * Affiche l'erreur dans la console si AFFICHE_ERREUR est vrai
	 * @param chaine La chaîne à afficher
	 */
	static void afficherErreur(String chaine) {
		if (AFFICHER_ERREURS) {
			System.out.print(chaine);
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
		
		if (ignorance == null) {
			Traiteur traiteur = instructionsConnues.get(code);
			
			if (traiteur != null) {
				ignorance = traiteur.executer(executeur, instruction.parametres(), instruction.argument());
			} else {
				instructionInconnue(instruction);
			}
		} else {
			ignorance = ignorance.appliquerCode(code);
		}
	}

	/**
	 * Affiche un message expliquant que l'instruction est inconnue, avec ses paramètres
	 * @param instruction L'instruction
	 */
	private void instructionInconnue(RMInstruction instruction) {
		afficherErreur(" --> Instruction [" + instruction.code() + "] non déchiffrable "
				+ "<" + instruction.argument() + "> "
				+ Arrays.toString(instruction.parametres()) + "\n");
	}

	/**
	 * Exécute les instructions données les unes aprés les autres
	 * @param instructions La liste des instructions
	 */
	public void executer(List<RMInstruction> instructions) {
		instructions.forEach(this::executer);
	}
}
