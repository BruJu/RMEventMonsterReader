package fr.bruju.rmeventreader.implementation.formulareader.formule.valeur.simple;

/**
 * Valeur constante ou aléatoire entre deux bornes
 *  
 * @author Bruju
 *
 */
public class ValeurNumerique implements ValeurSimple {
	private int valeurMin;
	private int valeurMax;
	
	/**
	 * Valeur aléatoire entre deux bornes
	 * @param valeurMin Valeur minimale
	 * @param valeurMax Valeur maximale
	 */
	public ValeurNumerique(int valeurMin, int valeurMax) {
		this.valeurMin = valeurMin;
		this.valeurMax = valeurMax;
	}
	
	/**
	 * Constante
	 * @param valeurInitiale La valeur
	 */
	public ValeurNumerique(int valeurInitiale) {
		this.valeurMin = valeurInitiale;
		this.valeurMax = valeurInitiale;
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
	public boolean estGarantiePositive() {
		return valeurMin > 0;
	}

	
	@Override
	public int[] evaluer() {
		return new int[] {valeurMin, valeurMax};
	}


	public int getValue() {
		return valeurMin;
	}

	/* ======
	 * EQUALS
	 * ====== */
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + valeurMax;
		result = prime * result + valeurMin;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ValeurNumerique other = (ValeurNumerique) obj;
		if (valeurMax != other.valeurMax)
			return false;
		if (valeurMin != other.valeurMin)
			return false;
		return true;
	}

}
