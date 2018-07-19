package fr.bruju.rmeventreader.implementation.formulareader.formule.valeur.simple;

import fr.bruju.rmeventreader.implementation.formulareader.formule.valeur.Valeur;
import fr.bruju.rmeventreader.rmdatabase.Affectation;

/**
 * Valeur constante ou aléatoire entre deux bornes
 *  
 * @author Bruju
 *
 */
public class ValeurNumerique implements Valeur {
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

	@Override
	public Valeur evaluationPartielle(Affectation affectation) {
		return this;
	}


}
