package fr.bruju.rmeventreader.formule.base;

import fr.bruju.rmeventreader.formule.Valeur;
import fr.bruju.rmeventreader.formule.composant.Personnage;
import fr.bruju.rmeventreader.formule.composant.Statistique;

public class ValeurStatistique implements Valeur {
	private Statistique statistique;
	private Personnage personnage;

	public ValeurStatistique(Personnage personnage, Statistique statistique) {
		this.personnage = personnage;
		this.statistique = statistique;
	}

	private String getString() {
		return personnage + "." + statistique;
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
}
