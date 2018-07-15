package fr.bruju.rmeventreader.actionmakers.donnees;

public class Pointeur implements LeftValue, RightValue {
	private int pointeur;
	
	public Pointeur(int pointeur) {
		this.pointeur = pointeur;
	}
	
	public int get() {
		return pointeur;
	}
}
