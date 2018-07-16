package fr.bruju.rmeventreader.implementation.formulareader.formule;

public class ValeurTernaire implements Valeur {
	private Condition condition;
	private Valeur siVrai;
	private Valeur siFaux;
	
	public ValeurTernaire(Condition condition, Valeur siVrai, Valeur siFaux) {
		this.condition = condition;
		this.siFaux = siFaux;
		this.siVrai = siVrai;
	}

	@Override
	public int evaluerMin() throws NonEvaluableException, DependantDeStatistiquesEvaluation {
		return (condition.testerMin()) ? siVrai.evaluerMin() : siFaux.evaluerMin();
	}

	@Override
	public int evaluerMax() throws NonEvaluableException, DependantDeStatistiquesEvaluation {
		return (condition.testerMax()) ? siVrai.evaluerMax() : siFaux.evaluerMax();
	}



	@Override
	public int evaluer() throws NonEvaluableException, DependantDeStatistiquesEvaluation {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getPriorite() {
		return 0;
	}

	@Override
	public String getString() {
		return "[(" + condition.getString() +") ? " + siVrai.getString() + " : " + siFaux.getString() + ")]";
	}

	
	// Ces impl�mentations ne sont pas assez pr�cises (mais correctes dans la msure o� ces m�thodes
	// garantissent que si vrai est renvoy�, on est sur de la r�ponse, mais que renvoyer faux
	// signifie "On ne sait pas")
	
	@Override
	public boolean estGarantiePositive() {
		return siVrai.estGarantiePositive() && siFaux.estGarantiePositive();
	}
	
	@Override
	public boolean estGarantieDeLaFormeMPMoinsConstante() {
		return siVrai.estGarantieDeLaFormeMPMoinsConstante() && siFaux.estGarantieDeLaFormeMPMoinsConstante();
	}

	@Override
	public boolean estConstant() {
		return siVrai.estConstant() && siFaux.estConstant();
	}

	@Override
	public boolean concerneLesMP() {
		return siVrai.concerneLesMP() && siFaux.concerneLesMP();
	}

}
