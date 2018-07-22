package fr.bruju.rmeventreader.implementation.formulatracker.formule.bouton;

import fr.bruju.rmeventreader.implementation.formulatracker.formule.ComposantTernaire;
import fr.bruju.rmeventreader.implementation.formulatracker.formule.condition.Condition;
import fr.bruju.rmeventreader.implementation.formulatracker.simplification.VisiteurDeComposants;

public class BTernaire extends ComposantTernaire<Bouton> implements Bouton {
	public BTernaire(Condition condition, Bouton vrai, Bouton faux) {
		super(condition, vrai, faux);
	}
	

	@Override
	public void accept(VisiteurDeComposants visiteurDeComposant) {
		visiteurDeComposant.visit(this);
	}
}
