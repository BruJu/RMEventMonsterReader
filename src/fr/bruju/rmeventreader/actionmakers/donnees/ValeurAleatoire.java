package fr.bruju.rmeventreader.actionmakers.donnees;

public class ValeurAleatoire implements RightValue {
	private int valeurMin;
	private int valeurMax;
	
	public ValeurAleatoire(int valeurMin, int valeurMax) {
		this.valeurMin = valeurMin;
		this.valeurMax = valeurMax;
	}
	
	public int getMin() {
		return valeurMin;
	}
	public int getMax() {
		return valeurMax;
	}
}
