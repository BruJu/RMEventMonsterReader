package fr.bruju.rmeventreader.implementation.formulatracker.formule.valeur;

import fr.bruju.rmeventreader.implementation.formulatracker.contexte.personnage.Personnage;

public class VStatistique implements Valeur {
	private Personnage possesseur;
	private String statistique;
	
	
	
	public VStatistique(Personnage personnage, String nomStat) {
		this.possesseur = personnage;
		this.statistique = nomStat;
	}

}
