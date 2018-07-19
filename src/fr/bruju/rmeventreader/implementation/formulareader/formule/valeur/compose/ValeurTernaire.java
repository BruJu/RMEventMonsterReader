package fr.bruju.rmeventreader.implementation.formulareader.formule.valeur.compose;

import fr.bruju.rmeventreader.implementation.formulareader.formule.DependantDeStatistiquesEvaluation;
import fr.bruju.rmeventreader.implementation.formulareader.formule.FonctionEvaluation;
import fr.bruju.rmeventreader.implementation.formulareader.formule.NonEvaluableException;
import fr.bruju.rmeventreader.implementation.formulareader.formule.condition.Condition;
import fr.bruju.rmeventreader.implementation.formulareader.formule.valeur.Valeur;
import fr.bruju.rmeventreader.rmdatabase.Affectation;

public class ValeurTernaire implements Valeur {
	private Condition condition;
	private Valeur siVrai;
	private Valeur siFaux;
	
	public ValeurTernaire(Condition condition, Valeur siVrai, Valeur siFaux) {
		this.condition = condition;
		this.siFaux = siFaux;
		this.siVrai = siVrai;
	}
	
	public int evaluer(FonctionEvaluation fonction) throws NonEvaluableException, DependantDeStatistiquesEvaluation {
		return (fonction.tester(condition)) ? fonction.evaluate(siVrai) : fonction.evaluate(siFaux);
	}

	@Override
	public int evaluerMin() throws NonEvaluableException, DependantDeStatistiquesEvaluation {
		return evaluer(FonctionEvaluation.minimum);
	}

	@Override
	public int evaluerMax() throws NonEvaluableException, DependantDeStatistiquesEvaluation {
		return evaluer(FonctionEvaluation.maximum);
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
	public Valeur evaluationPartielle(Affectation affectation) {
		// TODO Auto-generated method stub
		return null;
	}



}
