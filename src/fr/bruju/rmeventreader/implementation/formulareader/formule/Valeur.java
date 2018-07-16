package fr.bruju.rmeventreader.implementation.formulareader.formule;


/**
 * 
 * 
 * 
 * Certains services de cette interface sont sans garantis, c'est � dire
 * que les valeurs peuvent d�clarer qu'elles n'ont pas des propri�t�s
 * qu'elles ont.
 * @author Bruju
 *
 */
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
	 * 
	 * Cette m�thode n'a aucune garantie. Si elle renvoie toujours faux,
	 * m�me si la valeur est une constante positive, elle serait correcte.
	 * @return Vrai si la valeur est toujours positive
	 */
	public boolean estGarantiePositive();
	
	/**
	 * 
	 * 
	 * Cette m�thode n'a aucune garantie. Si elle renvoie toujours faux,
	 * m�me si la valeur est de la forme MP - Constante
	 * @return
	 */
	public default boolean estGarantieDeLaFormeMPMoinsConstante() {
		return false;
	}
	
	/**
	 * 
	 * 
	 * Cette m�thode n'a aucune garantie. Si elle renvoie toujours faux,
	 * m�me si la valeur est constante
	 * @return
	 */
	public default boolean estConstant() {
		return false;
	}
	
	/**
	 * 
	 * Cette m�thode n'a aucune garantie. Si elle renvoie toujours faux,
	 * m�me si la valeur concerne les MP.
	 * @return
	 */
	public default boolean concerneLesMP() {
		return false;
	}
}
