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
}
