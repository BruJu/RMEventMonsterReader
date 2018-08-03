package fr.bruju.rmeventreader.implementation.recomposeur.composant;

/**
 * Element pouvant être stocké dans un espace mémoire (variable ou interrupteur)
 * 
 * @author Bruju
 *
 */
public interface CaseMemoire extends Element {
	@Override
	public CaseMemoire simplifier();
}
