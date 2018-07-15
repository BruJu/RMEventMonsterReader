package fr.bruju.rmeventreader.actionmakers.donnees.rework;

public class Pointeur implements LeftValue {
	private int pointeur;
	
	public Pointeur(int pointeur) {
		this.pointeur = pointeur;
	}
	
	public int get() {
		return pointeur;
	}
}
