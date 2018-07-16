package fr.bruju.rmeventreader.implementation.formulareader.formule;

public interface Valeur {
	public int evaluer() throws NonEvaluableException, DependantDeStatistiquesEvaluation;
	
	
	public default int evaluerMin() throws NonEvaluableException, DependantDeStatistiquesEvaluation {
		return evaluer();
	}

	public default int evaluerMax() throws NonEvaluableException, DependantDeStatistiquesEvaluation {
		return evaluer();
	}
	

	public int getPriorite();
	public String getString();
	/**
	 * Permet de savoir si on a la garantie que la valeur est positive
	 * @return Vrai si la valeur est toujours positive
	 */
	public boolean estPositif();
	
	public default boolean estDeLaFormeMPMoinsConstante() {
		return false;
	}
	
	public default boolean estConstant() {
		return false;
	}
	
	public default boolean concerneLesMP() {
		return false;
	}
}
