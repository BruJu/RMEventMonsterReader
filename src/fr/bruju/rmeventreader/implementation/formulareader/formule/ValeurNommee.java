package fr.bruju.rmeventreader.implementation.formulareader.formule;

public class ValeurNommee extends ValeurVariable {
	private String nom;
	
	public ValeurNommee(int idVar, String nom) {
		super(idVar);
		this.nom = nom;
	}
	
	@Override
	public String getString() {
		return nom;
	}
}
