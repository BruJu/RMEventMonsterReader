package fr.bruju.rmeventreader.implementation.formulareader.formule.valeur.simple;

import fr.bruju.rmeventreader.implementation.formulareader.formule.DependantDeStatistiquesEvaluation;
import fr.bruju.rmeventreader.implementation.formulareader.formule.NonEvaluableException;
import fr.bruju.rmeventreader.implementation.formulareader.formule.valeur.Valeur;
import fr.bruju.rmeventreader.implementation.formulareader.model.Personnage;
import fr.bruju.rmeventreader.implementation.formulareader.model.Statistique;
import fr.bruju.rmeventreader.rmdatabase.Affectation;

public class ValeurStatistique implements Valeur {
	private Statistique statistique;
	private Personnage personnage;

	public ValeurStatistique(Personnage personnage, Statistique statistique) {
		this.personnage = personnage;
		this.statistique = statistique;
	}

	@Override
	public String getString() {
		return personnage.getNom() + "." + statistique;
	}
	

	
	@Override
	public int getPriorite() {
		return 0;
	}


	@Override
	public boolean estGarantiePositive() {
		return true;
	}
	

	@Override
	public Valeur evaluationPartielle(Affectation affectation) {
		return this;
	}

	@Override
	public int[] evaluer() throws NonEvaluableException, DependantDeStatistiquesEvaluation {
		throw new DependantDeStatistiquesEvaluation();
	}

	

}
