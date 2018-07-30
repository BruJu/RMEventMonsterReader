package fr.bruju.rmeventreader.actionmakers.donnees;

public class ValeurFixe implements RightValue {
	public final int valeur;
	
	public ValeurFixe(int valeur) {
		this.valeur = valeur;
	}
	
	public int get() {
		return valeur;
	}
}
