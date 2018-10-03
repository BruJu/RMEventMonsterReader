package fr.bruju.rmeventreader.actionmakers;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

import fr.bruju.rmeventreader.actionmakers.controlleur.DechiffreurInstructions;
import fr.bruju.rmeventreader.actionmakers.controlleur.ExecuteurInstructions;
import fr.bruju.rmeventreader.actionmakers.reference.ReferenceEC;
import fr.bruju.rmeventreader.actionmakers.reference.ReferenceMap;
import fr.bruju.rmeventreader.dictionnaires.LecteurDeLCF$;
import fr.bruju.lcfreader.rmobjets.RMEvenement;
import fr.bruju.lcfreader.rmobjets.RMEvenementCommun;
import fr.bruju.lcfreader.rmobjets.RMInstruction;
import fr.bruju.lcfreader.rmobjets.RMMap;
import fr.bruju.lcfreader.rmobjets.RMPage;
import fr.bruju.rmeventreader.utilitaire.lambda.TriConsumer;

/**
 * Cette classe permet d'avoir un point d'entrée simple et pratique pour faire la communication entre exécuteur
 * et fichiers.
 * 
 * @author Bruju
 *
 */
public class Explorateur {
	/* ==============================================
	 * Niveau d'abstraction 1 : Lire des instructions
	 * ============================================== */
	
	/**
	 * Exécute l'exécuteur sur toutes les instructions données
	 * @param executeur L'exécuteur
	 * @param instructions La liste d'instructions
	 */
	public static void executer(ExecuteurInstructions executeur, List<RMInstruction> instructions) {
		DechiffreurInstructions dechiffreur = new DechiffreurInstructions(executeur);
		dechiffreur.executer(instructions);
	}

	/* ============================================
	 * Niveau d'abstraction 2 : Lire des évènements
	 * ============================================ */
	
	/**
	 * Exécute l'exécuteur sur l'évènement à l'endroit donné
	 * @param executeur L'exécuteur
	 * @param idMap Numéro de la map
	 * @param idEvenement Numéro de l'évènement
	 * @param idPage Numéro de la page
	 */
	public static void lireEvenement(ExecuteurInstructions executeur, int idMap, int idEvenement, int idPage) {
		executer(executeur, LecteurDeLCF$.getInstance().page(idMap, idEvenement, idPage).instructions());
	}

	/**
	 * Exécute l'exécuteur sur l'évènement commun donné
	 * @param executeur L'exécuteur
	 * @param idEvenement Le numéro de l'évènement commun
	 */
	public static void lireEvenementCommun(ExecuteurInstructions executeur, int idEvenement) {
		executer(executeur, LecteurDeLCF$.getInstance().evenementCommun(idEvenement).instructions());
	}
	
	

	/* ================================================================
	 * Niveau d'abstraction 3 : Lire tous les évènements d'un même type
	 * ================================================================ */
	
	/**
	 * Permet d'explorer tout un projet
	 * @param actionSurLesEvenementCommuns Action réalisée pour chaque évènement commun
	 */
	public static void explorerEvenementsCommuns(Consumer<RMEvenementCommun> actionSurLesEvenementCommuns) {
		LecteurDeLCF$.getInstance().evenementsCommuns().values().forEach(actionSurLesEvenementCommuns::accept);
	}

	/**
	 * Permet d'explorer tout un projet
	 * @param actionSurLesPages Action réalisée pour chaque page d'évènement de chaque carte
	 */
	public static void explorerEvenements(TriConsumer<RMMap, RMEvenement, RMPage> actionSurLesPages) {
		// Applique à chaque page de chaque évènement de la map
		Consumer<RMMap> actionMap = rmMap -> rmMap.evenements().values().forEach(ev -> ev.pages().forEach(page ->
							actionSurLesPages.consume(rmMap, ev, page)));
		LecteurDeLCF$.getInstance().maps().values().forEach(actionMap);
	}


	/* =======================================================================================
	 * Niveau d'abstraction 4 : Lire tous les évènements d'un même type en retenant l'identité
	 * ======================================================================================= */
	
	
	public static void referencerEvenementsCommuns(Function<ReferenceEC, ExecuteurInstructions> generateur) {
		for (RMEvenementCommun evenementCommun : LecteurDeLCF$.getInstance().evenementsCommuns().values()) {
			executer(generateur.apply(new ReferenceEC(evenementCommun)), evenementCommun.instructions());
		}
	}
	
	public static void referencerCartes(Function<ReferenceMap, ExecuteurInstructions> generateur) {
		for (RMMap carte : LecteurDeLCF$.getInstance().maps().values()) {
			for (RMEvenement evenement : carte.evenements().values()) {
				for (RMPage page : evenement.pages()) {
					executer(generateur.apply(new ReferenceMap(carte, evenement, page)), page.instructions());
				}
			}
		}
	}
	
}
