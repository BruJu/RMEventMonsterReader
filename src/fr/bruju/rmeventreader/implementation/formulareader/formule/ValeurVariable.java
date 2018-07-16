package fr.bruju.rmeventreader.implementation.formulareader.formule;

import fr.bruju.rmeventreader.implementation.formulareader.actionmaker.Etat;

public class ValeurVariable implements Valeur {
	private int idVariable;
	
	public ValeurVariable (int idVariable) {
		this.idVariable = idVariable;
	}

	@Override
	public int getPriorite() {
		return 0;
	}

	@Override
	public String getString() {
		Etat.updateStatic(idVariable);
		return "V[" + idVariable + "]";
	}
	
	@Override
	public int evaluate() throws CantEvaluateException {
		throw new CantEvaluateException();
	}
}
