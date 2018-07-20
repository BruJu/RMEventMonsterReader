package fr.bruju.rmeventreader.implementation.formulareader.formule.valeur.simple;

import fr.bruju.rmeventreader.implementation.formulareader.formule.DependantDeStatistiquesEvaluation;
import fr.bruju.rmeventreader.implementation.formulareader.formule.NonEvaluableException;
import fr.bruju.rmeventreader.implementation.formulareader.model.Personnage;
import fr.bruju.rmeventreader.implementation.formulareader.model.Statistique;

public class ValeurStatistique implements ValeurSimple {
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
	public boolean estGarantiePositive() {
		return true;
	}
	

	@Override
	public int[] evaluer() throws NonEvaluableException, DependantDeStatistiquesEvaluation {
		throw new DependantDeStatistiquesEvaluation();
	}
	

}
