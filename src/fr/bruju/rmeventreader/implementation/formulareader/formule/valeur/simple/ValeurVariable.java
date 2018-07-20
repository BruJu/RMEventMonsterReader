package fr.bruju.rmeventreader.implementation.formulareader.formule.valeur.simple;


import fr.bruju.rmeventreader.implementation.formulareader.formule.DependantDeStatistiquesEvaluation;
import fr.bruju.rmeventreader.implementation.formulareader.formule.NewValeur;
import fr.bruju.rmeventreader.implementation.formulareader.formule.NonEvaluableException;
import fr.bruju.rmeventreader.implementation.formulareader.formule.valeur.Valeur;
import fr.bruju.rmeventreader.rmdatabase.Affectation;

public class ValeurVariable implements ValeurSimple {
	private int idVariable;
	
	public ValeurVariable (int idVariable) {
		this.idVariable = idVariable;
	}

	public int getIdVariable() {
		return idVariable;
	}
	
	


	@Override
	public String getString() {
		return "V[" + idVariable + "]";
	}
	
	@Override
	public boolean estGarantiePositive() {
		return false;
	}

	@Override
	public Valeur evaluationPartielle(Affectation affectation) {
		Integer etat = affectation.getVariable(idVariable);
		
		if (etat == null) {
			return this;
		} else {
			return NewValeur.Numerique(etat);
		}
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
		ValeurVariable other = (ValeurVariable) obj;
		if (idVariable != other.idVariable)
			return false;
		return true;
	}



}
