package fr.bruju.rmeventreader.implementation.formulareader.formule.valeur.compose;

import fr.bruju.rmeventreader.implementation.formulareader.formule.DependantDeStatistiquesEvaluation;
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
	
	@Override
	public int[] evaluer() throws NonEvaluableException, DependantDeStatistiquesEvaluation {
		boolean[] conditions = condition.tester();
		
		boolean conditionMin = conditions[0];
		boolean conditionMax = conditions[1];
		
		int[] siVraiRes = null;
		int[] siFauxRes = null;
		
		if (conditionMin || conditionMax) {
			siVraiRes = siVrai.evaluer();
		}
		
		if (!conditionMin || !conditionMax) {
			siFauxRes = siFaux.evaluer();
		}
		
		int valeurMin = conditionMin ? siVraiRes[0] : siFauxRes[0];
		int valeurMax = conditionMin ? siVraiRes[1] : siFauxRes[1];
		
		return new int[] {valeurMin, valeurMax};
	}

	@Override
	public int getPriorite() {
		return 0;
	}

	@Override
	public String getString() {
		return "[(" + condition.getString() +") ? " + siVrai.getString() + " : " + siFaux.getString() + "]";
	}

	
	// Ces implémentations ne sont pas assez précises (mais correctes dans la msure où ces méthodes
	// garantissent que si vrai est renvoyé, on est sur de la réponse, mais que renvoyer faux
	// signifie "On ne sait pas")
	
	@Override
	public boolean estGarantiePositive() {
		return siVrai.estGarantiePositive() && siFaux.estGarantiePositive();
	}
	


	@Override
	public Valeur evaluationPartielle(Affectation affectation) {
		Boolean resultat = condition.resoudre(affectation);
		
		if (resultat == null) {
			return this;
		} else {
			if (resultat == Boolean.TRUE) {
				return siVrai.evaluationPartielle(affectation);
			} else {
				return siFaux.evaluationPartielle(affectation);
			}
		}
	}



}
