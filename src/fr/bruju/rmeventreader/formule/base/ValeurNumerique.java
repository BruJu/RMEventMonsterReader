package fr.bruju.rmeventreader.formule.base;

import fr.bruju.rmeventreader.formule.Valeur;

public class ValeurNumerique implements Valeur {
	private int valeurMin;
	private int valeurMax;
	
	
	public ValeurNumerique(int valeurMin, int valeurMax) {
		this.valeurMin = valeurMin;
		this.valeurMax = valeurMax;
	}
	
	@Override
	public String getStringMin() {
		return Integer.toString(valeurMin);
	}

	@Override
	public String getStringMax() {
		return Integer.toString(valeurMax);
	}

	@Override
	public int getPriorite() {
		return 0;
	}

}
