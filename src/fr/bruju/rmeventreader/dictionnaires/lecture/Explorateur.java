package fr.bruju.rmeventreader.dictionnaires.lecture;

import java.util.List;
import java.util.function.Consumer;

import fr.bruju.rmeventreader.actionmakers.executeur.controlleur.DechiffreurInstructions;
import fr.bruju.rmeventreader.actionmakers.executeur.controlleur.ExecuteurInstructions;
import fr.bruju.rmeventreader.dictionnaires.header.Evenement;
import fr.bruju.rmeventreader.dictionnaires.header.EvenementCommun;
import fr.bruju.rmeventreader.dictionnaires.header.Instruction;
import fr.bruju.rmeventreader.dictionnaires.header.MapGeneral;
import fr.bruju.rmeventreader.dictionnaires.header.Page;
import fr.bruju.rmeventreader.dictionnaires.liblcfreader.LecteurDeCache;
import fr.bruju.rmeventreader.utilitaire.Pair;
import fr.bruju.rmeventreader.utilitaire.lambda.TriConsumer;

public class Explorateur {

	
	/**
	 * Permet d'explorer tout un projet
	 * @param actionSurLesEvenementCommuns Action réalisée pour chaque évènement commun
	 * @param actionSurLesPages Action réalisée pour chaque page d'évènement de chaque carte
	 */
	public void explorer(Consumer<EvenementCommun> actionSurLesEvenementCommuns,
			TriConsumer<MapGeneral, Evenement, Page> actionSurLesPages) {
		Pair<Integer, List<Integer>> infos = LecteurDeCache.getInformations();
		
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
	private void explorerMap(Integer numero, TriConsumer<MapGeneral, Evenement, Page> action) {
		MapGeneral map = LecteurDeCache.getMapGeneral(numero);
		List<Evenement> evenements = LecteurDeCache.getEvenementsDepuisMapGeneral(map);
		evenements.forEach(evenement -> evenement.pages.forEach(page -> action.consume(map, evenement, page)));
	}

	
	public static void executer(ExecuteurInstructions executeur, List<Instruction> instructions) {
		DechiffreurInstructions dechiffreur = new DechiffreurInstructions(executeur);
		dechiffreur.executer(instructions);
	}
	
	
	
}