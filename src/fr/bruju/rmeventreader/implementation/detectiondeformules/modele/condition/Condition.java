package fr.bruju.rmeventreader.implementation.detectiondeformules.modele.condition;

/**
 * Une condition (utilisée dans les branchements conditionnels)
 */
public interface Condition {
	/**
	 * Donne une représentation textuelle de la condition
	 * @return Une représentation textuelle
	 */
	String getString();

	/**
	 * Teste la condition.
	 * @return Vrai ou faux si la condition est évaluable. Null si elle n'est pas évaluable
	 */
	Boolean evaluer();
}