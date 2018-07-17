package fr.bruju.rmeventreader.implementation.formulareader.formule;

import java.util.ArrayList;
import java.util.List;

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

	@Override
	public List<Valeur> splash() {
		List<Valeur> list = new ArrayList<>();
		list.add(this);
		return list;
	}
}
