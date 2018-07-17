package fr.bruju.rmeventreader.implementation.formulareader.formule;

public class ValeurNommee extends ValeurVariable {
	private String nom;
	
	ValeurNommee(int idVar, String nom) {
		super(idVar);
		this.nom = nom.substring(1);
	}
	
	@Override
	public String getString() {
		return nom;
	}
}
