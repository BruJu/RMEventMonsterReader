package fr.bruju.rmeventreader.implementation.formulatracker.composant;

/**
 * Un composant feuille est un composant n'ayant pas de fils.
 * 
 * @author Bruju
 *
 */
public interface ComposantFeuille extends Composant {
	@Override
	default Composant evaluationRapide() {
		return this;
	}
}
