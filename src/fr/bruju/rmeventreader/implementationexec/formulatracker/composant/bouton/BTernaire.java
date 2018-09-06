package fr.bruju.rmeventreader.implementationexec.formulatracker.composant.bouton;

import fr.bruju.rmeventreader.implementationexec.formulatracker.composant.ComposantTernaire;
import fr.bruju.rmeventreader.implementationexec.formulatracker.composant.condition.Condition;
import fr.bruju.rmeventreader.implementationexec.formulatracker.composant.visiteur.VisiteurDeComposants;

/**
 * Etat d'un bouton dépendant d'une condition
 * 
 * @author Bruju
 *
 */
public class BTernaire extends ComposantTernaire<Bouton> implements Bouton {
	/* =========
	 * COMPOSANT
	 * ========= */

	/**
	 * Crée un bouton dont l'état dépend d'une condition
	 * @param condition La condition
	 * @param vrai La valeur du bouton si la condition est vraie
	 * @param faux La valeur du bouton si la condition est fausse
	 */
	public BTernaire(Condition condition, Bouton vrai, Bouton faux) {
		super(condition, vrai, faux);
	}

	/* ========
	 * VISITEUR
	 * ======== */

	@Override
	public void accept(VisiteurDeComposants visiteurDeComposant) {
		visiteurDeComposant.visit(this);
	}
}
