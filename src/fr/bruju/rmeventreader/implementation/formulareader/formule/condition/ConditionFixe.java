package fr.bruju.rmeventreader.implementation.formulareader.formule.condition;

import fr.bruju.rmeventreader.implementation.formulareader.formule.DependantDeStatistiquesEvaluation;
import fr.bruju.rmeventreader.implementation.formulareader.formule.NonEvaluableException;
import fr.bruju.rmeventreader.rmdatabase.Affectation;
import fr.bruju.rmeventreader.rmdatabase.AffectationFlexible;

public class ConditionFixe implements Condition {
	boolean valeur;
	
	
	public ConditionFixe(boolean valeur) {
		this.valeur = valeur;
	}

	@Override
	public String getString() {
		return valeur ? "Vrai" : "Faux";
	}

	@Override
	public Condition revert() {
		return new ConditionFixe(!valeur);
	}

	@Override
	public Boolean resoudre(Affectation affectation) {
		return valeur;
	}

	@Override
	public boolean[] tester() throws NonEvaluableException, DependantDeStatistiquesEvaluation {
		return new boolean[] {valeur, valeur};
	}

	@Override
	public Condition evaluationPartielle(Affectation affectation) {
		return this;
	}
	
	@Override
	public void modifierAffectation(AffectationFlexible affectation) throws AffectationNonFaisable {
		if (!valeur) {
			throw new AffectationNonFaisable();
		}
	}

}
