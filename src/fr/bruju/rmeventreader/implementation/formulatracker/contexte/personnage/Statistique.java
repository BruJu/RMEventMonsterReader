package fr.bruju.rmeventreader.implementation.formulatracker.contexte.personnage;

public class Statistique {
	private final Personnage possesseur;
	private final String nom;
	private final int position;
	
	public Statistique(Personnage possesseur, String nom, int position) {
		this.possesseur = possesseur;
		this.nom = nom;
		this.position = position;
	}

	public Personnage getPossesseur() {
		return possesseur;
	}

	public String getNom() {
		return nom;
	}
	
	public int getPosition() {
		return position;
	}
}
