package fr.bruju.rmeventreader.implementation.formulatracker.composant.valeur;

import fr.bruju.rmeventreader.implementation.formulatracker.composant.ComposantTernaire;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.condition.Condition;
import fr.bruju.rmeventreader.implementation.formulatracker.simplification.VisiteurDeComposants;

/**
 * Valeur dépendant d'une condition
 * @author Bruju
 *
 */
public class VTernaire extends ComposantTernaire<Valeur> implements Valeur {
	/* =========
	 * COMPOSANT
	 * ========= */

	/**
	 * Crée une valeur dépendant de la condition
	 * @param condition La condition
	 * @param vrai La valeur si la condition est vraie
	 * @param faux La valeur si la condition est fausse
	 */
	public VTernaire(Condition condition, Valeur v1, Valeur v2) {
		super(condition, v1, v2);
	}

	/* ========
	 * VISITEUR
	 * ======== */

	@Override
	public void accept(VisiteurDeComposants visiteurDeComposant) {
		visiteurDeComposant.visit(this);
	}
}
