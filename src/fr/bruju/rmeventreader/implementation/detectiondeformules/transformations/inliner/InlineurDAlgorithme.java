package fr.bruju.rmeventreader.implementation.detectiondeformules.transformations.inliner;


import fr.bruju.rmeventreader.implementation.detectiondeformules.modele.expression.ExprVariable;
import fr.bruju.rmeventreader.implementation.detectiondeformules.modele.algorithme.Algorithme;
import fr.bruju.rmeventreader.implementation.detectiondeformules.nouvellestransformations.TransformationDeTable;
import fr.bruju.util.table.Enregistrement;
import fr.bruju.util.table.Table;

import java.util.List;

/**
 * Transforme un algorithme pour :
 * <br>- Supprimer les instructions mortes
 * <br>- Inliner certaines instructions du type "a = y + z; b = a + b; a = 3;" en "b = y + z + b; a = 3;"
 * <br><br>L'objectif étant de réduire le nombre de lignes de l'algorithme afin de le rendre plus compréhensible pour
 * un humain.
 */
public class InlineurDAlgorithme implements TransformationDeTable {
	@Override
	public Table appliquer(Table table) {
		table.forEach(this::remplacerAlgorithme);
		return table;
	}

	private void remplacerAlgorithme(Enregistrement enregistrement) {
		List<ExprVariable> variablesVivantes = enregistrement.get("Sorties");
		Algorithme algorithme = enregistrement.get("Algorithme");
		enregistrement.set("Algorithme", simplifier(variablesVivantes, algorithme));
	}

	/**
	 * Simplifie l'algorithme donné en considérant que les variables présentes dans variablesDeSorties sont les
	 * variables vivantes à la fin de l'algorithme.
	 * <br>L'algorithme retourné a la même sémantique que celui donné, mais les instructions mortes (n'affectant pas la
	 * valeur des variables de sorties) sont retirées, et certaines instructions sont condensées.
	 * @param variablesDeSortie La liste des variables vivantes en sortie
	 * @param algorithme L'algorithme à transformer
	 * @return L'algorithme transformé
	 */
	public static Algorithme simplifier(List<ExprVariable> variablesDeSortie, Algorithme algorithme) {
		// Etape 1 : Lecture de bas en haut pour détecter les instructions inutiles / inlinables
		AnalyseurDUtilisationsDesInstructions analyseur = new AnalyseurDUtilisationsDesInstructions(variablesDeSortie);
		algorithme.acceptInverse(analyseur);
		// Etape 2 : Reecriture de haut en bas avec les informations détectées
		return ReecrivainDAlgoSansInstructMortes.reecrireSansAffectationMorte(algorithme, analyseur);
	}
}
