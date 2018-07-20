package fr.bruju.rmeventreader.implementation.formulareader.formule.condition;

import fr.bruju.rmeventreader.implementation.formulareader.formule.DependantDeStatistiquesEvaluation;
import fr.bruju.rmeventreader.implementation.formulareader.formule.NonEvaluableException;
import fr.bruju.rmeventreader.implementation.formulareader.formule.valeur.Valeur;

public class ConditionSwitch implements Condition {

	private Valeur interrupteur;
	private boolean value;

	public ConditionSwitch(Valeur interrupteur, boolean value) {
		this.interrupteur = interrupteur;
		this.value = value;
	}

	@Override
	public boolean[] tester() throws NonEvaluableException, DependantDeStatistiquesEvaluation {
		int[] evaluation = interrupteur.evaluer();
		int v = (value) ? 1 : 0;
		
		
		boolean[] resultat = new boolean[] {v == evaluation[0], v == evaluation[1]};
		
		return resultat;
	}

	@Override
	public String getString() {
		return ((value) ? "" : "!") + interrupteur.getString();
	}

	@Override
	public Condition revert() {
		return new ConditionSwitch(interrupteur, !value);
	}



	@Override
	public Condition integrerCondition(Condition aInclure) {
		// Types identiques
		if (!(aInclure instanceof ConditionSwitch)) {
			return this;
		}
		
		ConditionSwitch aInc = (ConditionSwitch) aInclure;
		
		// Concerne la même valeur
		if (!(interrupteur.equals(aInc.interrupteur))) {
			return this;
		}
		
		// Simplification
		return ConditionFixe.get(value == aInc.value);
	}


}
