package fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele;

public class Constante implements Expression {
	public final int valeur;

	@Override
	public String getString() {
		return Integer.toString(valeur);
	}

	public Constante(int valeur) {
		this.valeur = valeur;
	}
	
	
	
}
