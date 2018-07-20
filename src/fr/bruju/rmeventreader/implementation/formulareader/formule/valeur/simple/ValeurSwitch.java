package fr.bruju.rmeventreader.implementation.formulareader.formule.valeur.simple;

import fr.bruju.rmeventreader.implementation.formulareader.formule.DependantDeStatistiquesEvaluation;
import fr.bruju.rmeventreader.implementation.formulareader.formule.NonEvaluableException;

public class ValeurSwitch implements ValeurSimple {
	private int idVariable;
	
	public ValeurSwitch (int idVariable) {
		this.idVariable = idVariable;
	}
	
	public int getIdSwitch() {
		return idVariable;
	}
	

	@Override
	public int getPriorite() {
		return 0;
	}

	@Override
	public String getString() {
		return "S[" + idVariable + "]";
	}
	
	@Override
	public boolean estGarantiePositive() {
		return false;
	}

	@Override
	public int[] evaluer() throws NonEvaluableException, DependantDeStatistiquesEvaluation {
		throw new NonEvaluableException();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + idVariable;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ValeurSwitch other = (ValeurSwitch) obj;
		if (idVariable != other.idVariable)
			return false;
		return true;
	}




}
