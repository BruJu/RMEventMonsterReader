package fr.bruju.rmeventreader.actionmakers.executeur.controlleur;

import java.util.List;
import java.util.Set;
import java.util.function.Consumer;

import fr.bruju.rmeventreader.dictionnaires.liblcfreader.LecteurDeCache;
import fr.bruju.rmeventreader.dictionnaires.modele.Evenement;
import fr.bruju.rmeventreader.dictionnaires.modele.EvenementCommun;
import fr.bruju.rmeventreader.dictionnaires.modele.Instruction;
import fr.bruju.rmeventreader.dictionnaires.modele.MapGeneral;
import fr.bruju.rmeventreader.dictionnaires.modele.Page;
import fr.bruju.rmeventreader.utilitaire.Pair;
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
	public static void explorer(Consumer<EvenementCommun> actionSurLesEvenementCommuns,
			TriConsumer<MapGeneral, Evenement, Page> actionSurLesPages) {
		Pair<Integer, Set<Integer>> infos = LecteurDeCache.getInformations();
		
		if (actionSurLesEvenementCommuns != null) {
			for (int i = 1 ; i <= infos.getLeft() ; i++) { 
				actionSurLesEvenementCommuns.accept(LecteurDeCache.getEvenementCommun(i));
			}
		}
		
		if (actionSurLesPages != null) {
			infos.getRight().forEach(numero -> explorerMap(numero, actionSurLesPages));
		}
	}
	
	/**
	 * Explore tous les évènements d'une map
	 * @param numero Le numéro de la map
	 * @param action Action à réaliser pour chaque page d'évènement
	 */
	private static void explorerMap(Integer numero, TriConsumer<MapGeneral, Evenement, Page> action) {
		MapGeneral map = LecteurDeCache.getMapGeneral(numero);
		List<Evenement> evenements = LecteurDeCache.getEvenementsDepuisMapGeneral(map);
		evenements.forEach(evenement -> evenement.pages.forEach(page -> action.consume(map, evenement, page)));
	}

	/**
	 * Exécute l'exécuteur sur toutes les instructions données
	 * @param executeur L'exécuteur
	 * @param instructions La liste d'instructions
	 */
	public static void executer(ExecuteurInstructions executeur, List<Instruction> instructions) {
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
		Evenement evenement = LecteurDeCache.getEvenement(idMap, idEvenement);
		executer(executeur, evenement.pages.get(idPage - 1).instructions);
	}

	/**
	 * Exécute l'exécuteur sur l'évènement commun donné
	 * @param executeur L'exécuteur
	 * @param idEvenement Le numéro de l'évènement commun
	 */
	public static void lireEvenementCommun(ExecuteurInstructions executeur, int idEvenement) {
		EvenementCommun evenementCommun = LecteurDeCache.getEvenementCommun(idEvenement);
		executer(executeur, evenementCommun.instructions);
	}

	
}
