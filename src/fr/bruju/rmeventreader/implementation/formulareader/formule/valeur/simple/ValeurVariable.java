package fr.bruju.rmeventreader.implementation.formulareader.formule.valeur.simple;

import fr.bruju.rmeventreader.implementation.formulareader.formule.DependantDeStatistiquesEvaluation;
import fr.bruju.rmeventreader.implementation.formulareader.formule.NewValeur;
import fr.bruju.rmeventreader.implementation.formulareader.formule.NonEvaluableException;
import fr.bruju.rmeventreader.implementation.formulareader.formule.valeur.Valeur;
import fr.bruju.rmeventreader.rmdatabase.Affectation;

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
	public int evaluerMin() throws NonEvaluableException, DependantDeStatistiquesEvaluation {
		throw new NonEvaluableException();
	}

	@Override
	public int evaluerMax() throws NonEvaluableException, DependantDeStatistiquesEvaluation {
		throw new NonEvaluableException();
	}

}
