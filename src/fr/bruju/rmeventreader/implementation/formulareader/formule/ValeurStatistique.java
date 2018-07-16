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

	private String getString() {
		return personnage.getNom() + "." + statistique;
	}
	
	@Override
	public String getStringMin() {
		return getString();
	}

	@Override
	public String getStringMax() {
		return getString();
	}
	
	@Override
	public int getPriorite() {
		return 0;
	}

	@Override
	public String getStringUnique() {
		return getString();
	}
}
