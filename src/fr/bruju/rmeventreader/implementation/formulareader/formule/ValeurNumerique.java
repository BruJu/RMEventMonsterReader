package fr.bruju.rmeventreader.implementation.formulareader.formule;

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

	@Override
	public String getStringUnique() {
		if (valeurMin == valeurMax) {
			return Integer.toString(valeurMin);
		} else {
			return Integer.toString(valeurMin) + "~" + Integer.toString(valeurMax);
		}
	}

}
