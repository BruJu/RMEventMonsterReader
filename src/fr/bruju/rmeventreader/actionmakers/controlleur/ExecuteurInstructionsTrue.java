package fr.bruju.rmeventreader.actionmakers.controlleur;

/**
 * Permet de déclarer un exécuteur qui explore toutes les branches par défaut
 * 
 * @author Bruju
 *
 */
public interface ExecuteurInstructionsTrue extends ExecuteurInstructions {
	@Override
	public default boolean getBooleenParDefaut() {
		return true;
	}
}
