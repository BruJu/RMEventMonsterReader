package fr.bruju.rmeventreader.implementation.formulareader.formule.valeur.simple;

import fr.bruju.rmeventreader.implementation.formulareader.formule.DependantDeStatistiquesEvaluation;
import fr.bruju.rmeventreader.implementation.formulareader.formule.NewValeur;
import fr.bruju.rmeventreader.implementation.formulareader.formule.NonEvaluableException;
import fr.bruju.rmeventreader.implementation.formulareader.formule.valeur.Valeur;
import fr.bruju.rmeventreader.rmdatabase.Affectation;

public class ValeurSwitch implements Valeur {
	private int idVariable;
	
	public ValeurSwitch (int idVariable) {
		this.idVariable = idVariable;
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
	public Valeur evaluationPartielle(Affectation affectation) {
		Boolean etat = affectation.getInterrupteur(idVariable);
		
		if (etat == null) {
			return this;
		} else {
			return NewValeur.Booleen(etat);
		}
	}

	@Override
	public int[] evaluer() throws NonEvaluableException, DependantDeStatistiquesEvaluation {
		throw new NonEvaluableException();
	}

}
