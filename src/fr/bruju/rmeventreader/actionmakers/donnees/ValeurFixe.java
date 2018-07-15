package fr.bruju.rmeventreader.actionmakers.donnees;

public class ValeurFixe implements RightValue {
	private int valeur;
	
	public ValeurFixe(int valeur) {
		this.valeur = valeur;
	}
	
	public int get() {
		return valeur;
	}
}
