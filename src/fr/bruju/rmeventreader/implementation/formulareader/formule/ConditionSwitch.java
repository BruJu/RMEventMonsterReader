package fr.bruju.rmeventreader.implementation.formulareader.formule;

public class ConditionSwitch implements Condition {

	private Valeur interrupteur;
	private boolean value;

	public ConditionSwitch(Valeur interrupteur, boolean value) {
		this.interrupteur = interrupteur;
		this.value = value;
	}
	
	@Override
	public boolean tester() throws NonEvaluableException, DependantDeStatistiquesEvaluation {
		return (interrupteur.evaluer() == 1) == value;
	}

	@Override
	public boolean testerMax() throws NonEvaluableException, DependantDeStatistiquesEvaluation {
		return tester();
	}

	@Override
	public boolean testerMin() throws NonEvaluableException, DependantDeStatistiquesEvaluation {
		return tester();
	}

	@Override
	public String getString() {
		return interrupteur.getString();
	}

}
