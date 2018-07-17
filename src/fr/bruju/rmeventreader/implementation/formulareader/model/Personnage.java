package fr.bruju.rmeventreader.implementation.formulareader.model;

import java.util.Map;

public class Personnage {
	private String nom;
	private Map<Integer, Statistique> statistiques;
	
	public Personnage(String nom, Map<Integer, Statistique> statistiques) {
		this.nom = nom;
		this.statistiques = statistiques;
	}
	
	public String getNom() {
		return nom;
	}
	
	public Map<Integer, Statistique> getStatistiques() {
		return statistiques;
	}
	
	
	
	public String subFormula(String str) {
		String[] remplacements = {
				"(100 - max("+nom+"."+Statistique.Defense +", 400) / 4) / 100", "MALUSFATIGUE",
				"(" + nom+"."+Statistique.BonusMagieOff+" + 10) / 10", "CAPAMAGIEOFF",
				"(" + nom+"."+Statistique.BonusPhysique+" + 10) / 10", "CAPAPHYSIQUE",
				nom + ".", "",
				
				
		};
		
		String nouveauStr = str;
		
		//System.out.println(remplacements[0]);
		
		for (int i = 0 ; i != remplacements.length ; i = i + 2) {
			nouveauStr = nouveauStr.replace(remplacements[i], remplacements[i+1]);
		}
		
		return nouveauStr;
	}
}
