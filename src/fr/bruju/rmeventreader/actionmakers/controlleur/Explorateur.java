package fr.bruju.rmeventreader.actionmakers.controlleur;

import java.util.List;
import java.util.function.Consumer;

import fr.bruju.rmeventreader.dictionnaires.LecteurDeLCF$;
import fr.bruju.lcfreader.rmobjets.RMEvenement;
import fr.bruju.lcfreader.rmobjets.RMEvenementCommun;
import fr.bruju.lcfreader.rmobjets.RMFabrique;
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
	/**
	 * Permet d'explorer tout un projet
	 * @param actionSurLesEvenementCommuns Action réalisée pour chaque évènement commun
	 * @param actionSurLesPages Action réalisée pour chaque page d'évènement de chaque carte
	 */
	public static void explorer(Consumer<RMEvenementCommun> actionSurLesEvenementCommuns,
			TriConsumer<RMMap, RMEvenement, RMPage> actionSurLesPages) {
		RMFabrique usine = LecteurDeLCF$.getInstance();
		
		if (actionSurLesEvenementCommuns != null) {
			usine.evenementsCommuns().values().forEach(actionSurLesEvenementCommuns::accept);
		}
		
		if (actionSurLesPages != null) {
			// Applique à chaque page de chaque évènement de la map
			Consumer<RMMap> actionMap = rmMap -> rmMap.evenements().values().forEach(ev -> ev.pages().forEach(page ->
								actionSurLesPages.consume(rmMap, ev, page)));
			
			usine.maps().values().forEach(actionMap);
		}
	}

	/**
	 * Exécute l'exécuteur sur toutes les instructions données
	 * @param executeur L'exécuteur
	 * @param instructions La liste d'instructions
	 */
	public static void executer(ExecuteurInstructions executeur, List<RMInstruction> instructions) {
		DechiffreurInstructions dechiffreur = new DechiffreurInstructions(executeur);
		dechiffreur.executer(instructions);
	}
	
	/**
	 * Exécute l'exécuteur sur l'évènement à l'endroit donné
	 * @param executeur L'exécuteur
	 * @param idMap Numéro de la map
	 * @param idEvenement Numéro de l'évènement
	 * @param idPage Numéro de la page
	 */
	public static void lireEvenementMap(ExecuteurInstructions executeur, int idMap, int idEvenement, int idPage) {
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
}
