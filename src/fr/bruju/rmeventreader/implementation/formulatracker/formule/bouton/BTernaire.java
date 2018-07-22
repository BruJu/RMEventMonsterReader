package fr.bruju.rmeventreader.implementation.formulatracker.formule.bouton;

import fr.bruju.rmeventreader.implementation.formulatracker.formule.ComposantTernaire;
import fr.bruju.rmeventreader.implementation.formulatracker.formule.condition.Condition;

public class BTernaire implements Bouton, ComposantTernaire<Bouton> {
	private Condition condition;
	private Bouton siVrai;
	private Bouton siFaux;

	public BTernaire(Condition condition, Bouton v1, Bouton v2) {
		this.condition = condition;
		this.siVrai = v1;
		this.siFaux = v2;
	}

	@Override
	public Condition getCondition() {
		return condition;
	}

	@Override
	public Bouton getVrai() {
		return siVrai;
	}

	@Override
	public Bouton getFaux() {
		return siFaux;
	}
}
