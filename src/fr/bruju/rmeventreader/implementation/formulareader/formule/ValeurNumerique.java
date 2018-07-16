package fr.bruju.rmeventreader.implementation.formulareader.formule;

public class ValeurNumerique implements Valeur {
	private int valeurMin;
	private int valeurMax;
	
	
	public ValeurNumerique(int valeurMin, int valeurMax) {
		this.valeurMin = valeurMin;
		this.valeurMax = valeurMax;
	}
	
	public ValeurNumerique(int valeurInitiale) {
		this.valeurMin = valeurInitiale;
		this.valeurMax = valeurInitiale;
	}

	@Override
	public int getPriorite() {
		return 0;
	}

	@Override
	public String getString() {
		if (valeurMin == valeurMax) {
			return Integer.toString(valeurMin);
		} else {
			return Integer.toString(valeurMin) + "~" + Integer.toString(valeurMax);
		}
	}

	@Override
	public int evaluate() throws CantEvaluateException {
		return valeurMin;
	}

}
