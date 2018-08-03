package fr.bruju.rmeventreader.implementation.recomposeur.composant.bouton;

import fr.bruju.rmeventreader.implementation.recomposeur.composant.CaseMemoire;

/**
 * Représente un interrupteur dans RPG Maker
 * 
 * @author Bruju
 *
 */
public interface Bouton extends CaseMemoire {
	@Override
	public Bouton simplifier();
}
