package fr.bruju.rmeventreader.actionmakers.donnees.rework;

public class ValeurAleatoire {
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
