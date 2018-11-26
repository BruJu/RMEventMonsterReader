package fr.bruju.rmeventreader.implementation.detectiondeformules.modele.algorithme;

import fr.bruju.rmeventreader.implementation.detectiondeformules.modele.condition.Condition;

/**
 * Un visiteur d'algorithme
 */
public interface VisiteurDAlgorithme {
	/**
	 * Visite l'algorithme dans le sens conventionnel
	 * @param algorithme L'algorithme à visiter
	 */
	public default void visit(Algorithme algorithme) {
		algorithme.accept(this);
	}

	/** Visite l'instruction */
	public default void visit(InstructionGenerale instruction) {
		instruction.accept(this);
	}

	/** Visite le bloc conditionnel */
	public void visit(BlocConditionnel blocConditionnel);

	/** Visite l'instruction d'affectation */
	public void visit(InstructionAffectation instructionAffectation);

	/** Visite l'instruction d'affichage */
	public void visit(InstructionAffichage instructionAffichage);
}
