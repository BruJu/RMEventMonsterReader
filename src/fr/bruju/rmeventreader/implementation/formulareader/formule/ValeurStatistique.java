package fr.bruju.rmeventreader.implementation.formulareader.formule;

import fr.bruju.rmeventreader.implementation.formulareader.model.Personnage;
import fr.bruju.rmeventreader.implementation.formulareader.model.Statistique;

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
	public int evaluate() throws StatDependantEvaluation {
		throw new StatDependantEvaluation();
	}
}
