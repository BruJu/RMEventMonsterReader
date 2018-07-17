package fr.bruju.rmeventreader.implementation.formulareader.formule;

/**
 * Valeur constante ou aléatoire entre deux bornes
 *  
 * @author Bruju
 *
 */
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
	public int evaluer() throws NonEvaluableException {
		return valeurMin;
	}

	@Override
	public boolean estGarantiePositive() {
		return valeurMin > 0;
	}


	@Override
	public boolean estConstant() {
		return true;
	}
	
	@Override
	public int evaluerMin() {
		return valeurMin;
	}

	@Override
	public int evaluerMax() {
		return valeurMax;
	}

}
