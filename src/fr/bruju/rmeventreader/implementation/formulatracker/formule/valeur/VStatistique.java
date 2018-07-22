package fr.bruju.rmeventreader.implementation.formulatracker.formule.valeur;

import fr.bruju.rmeventreader.implementation.formulatracker.contexte.personnage.Personnage;

public class VStatistique implements Valeur {
	public final Personnage possesseur;
	public final String statistique;
	
	public VStatistique(Personnage personnage, String nomStat) {
		this.possesseur = personnage;
		this.statistique = nomStat;
	}



	@Override
	public String getString() {
		return possesseur.getNom() + "." + statistique;
	}

}
