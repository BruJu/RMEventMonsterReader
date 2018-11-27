package fr.bruju.rmeventreader.implementation.detectiondeformules.modele.algorithme;

import fr.bruju.rmeventreader.implementation.detectiondeformules.modele.visiteurs.VisiteurDAlgorithme;
import fr.bruju.util.IndentedStringBuilder;

/**
 * Une instruction
 */
public interface InstructionGenerale {
	/**
	 * Renvoie vrai si l'instruction ne modifie pas la mémoire
	 * @return Vrai si l'instruction ne modifie pas la mémoire
	 */
	boolean estVide();

	/**
	 * Visite l'instruction
	 * @param visiteur Le visiteur
	 */
	public void accept(VisiteurDAlgorithme visiteur);

	/**
	 * Renvoie vrai si les deux instructions sont identiques
	 * @param instructionGenerale L'instruction
	 * @return Vrai si cette instruction et l'instruction donnée sont identiques
	 */
	boolean estIdentique(InstructionGenerale instructionGenerale);

	/**
	 * Ajoute l'instruction aux instructions listées en vue de produire un affichage
	 * @param listeur Le listeur d'instructions
	 */
	void listerTextuellement(IndentedStringBuilder listeur);
}
