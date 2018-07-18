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
		return ((value) ? "" : "!") + interrupteur.getString();
	}

	@Override
	public Condition revert() {
		return new ConditionSwitch(interrupteur, !value);
	}

	@Override
	public int degreDeSimilitude(Condition autre) {
		if (autre == null)
			return 0;
		
		if (!(autre instanceof ConditionSwitch))
			return 0;
		
		ConditionSwitch autreS = (ConditionSwitch) autre;
		
		if (this.interrupteur != autreS.interrupteur)
			return 1;
		
		if (this.value != autreS.value)
			return 2;
		
		return 3;
	}

	@Override
	public String getStringApresAutre(Condition autre) {
		if (degreDeSimilitude(autre) == 3) {
			return "";
		}
		
		return getString();
	}

	@Override
	public Valeur estVariableIdentiqueA() {
		return null;
	}


}
