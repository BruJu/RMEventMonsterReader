package fr.bruju.rmeventreader.implementation.formulatracker.contexte.attaques;


public class Attaque {
	private final String nom;
	private final String cheminFichier;
	private Resultat resultat;
	
	public Attaque(String nom, String cheminFichier) {
		this.nom = nom;
		this.cheminFichier = cheminFichier;
	}

	public String getNom() {
		return nom;
	}
	
	public String getChemin() {
		return cheminFichier;
	}
	
	public void attacherResultat(Resultat resultat) {
		this.resultat = resultat;
	}
	
	public Resultat getResultat() {
		return resultat;
	}
	
	
}
