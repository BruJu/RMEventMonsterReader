package fr.bruju.rmeventreader.implementation.formulareader.formule;

import java.util.ArrayList;
import java.util.List;

public class ValeurTernaire implements Valeur {
	private Condition condition;
	private Valeur siVrai;
	private Valeur siFaux;
	
	ValeurTernaire(Condition condition, Valeur siVrai, Valeur siFaux) {
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
		return evaluerMin();
	}

	@Override
	public int getPriorite() {
		return 0;
	}

	@Override
	public String getString() {
		return "[(" + condition.getString() +") ? " + siVrai.getString() + " : " + siFaux.getString() + ")]";
	}

	
	// Ces implémentations ne sont pas assez précises (mais correctes dans la msure où ces méthodes
	// garantissent que si vrai est renvoyé, on est sur de la réponse, mais que renvoyer faux
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
	

	@Override
	public List<Valeur> splash() {
		// Faux pour les imbrications de ternaires sur les mêmes variables
		List<Valeur> list = new ArrayList<>();
		siVrai.splash().forEach(list::add);
		siFaux.splash().forEach(list::add);
		return list;
	}
}
