package fr.bruju.rmeventreader.implementation.formulareader.formule;

public class ValeurVariable implements Valeur {
	private int idVariable;
	
	ValeurVariable (int idVariable) {
		this.idVariable = idVariable;
	}

	@Override
	public int getPriorite() {
		return 0;
	}

	@Override
	public String getString() {
		return "V[" + idVariable + "]";
	}
	
	@Override
	public int evaluer() throws NonEvaluableException {
		throw new NonEvaluableException();
	}

	@Override
	public boolean estGarantiePositive() {
		return false;
	}

}
