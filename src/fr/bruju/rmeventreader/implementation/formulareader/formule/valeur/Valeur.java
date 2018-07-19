package fr.bruju.rmeventreader.implementation.formulareader.formule.valeur;

import fr.bruju.rmeventreader.implementation.formulareader.formule.DependantDeStatistiquesEvaluation;
import fr.bruju.rmeventreader.implementation.formulareader.formule.NonEvaluableException;
import fr.bruju.rmeventreader.rmdatabase.Affectation;

/**
 * 
 * 
 * 
 * Certains services de cette interface sont sans garantis, c'est à dire que les valeurs peuvent déclarer qu'elles n'ont
 * pas des propriétés qu'elles ont.
 * 
 * @author Bruju
 *
 */
public interface Valeur {
	public int evaluerMin() throws NonEvaluableException, DependantDeStatistiquesEvaluation;
	
	public int evaluerMax() throws NonEvaluableException, DependantDeStatistiquesEvaluation;

	public int getPriorite();

	public String getString();

	public Valeur evaluationPartielle(Affectation affectation);

	// Services non garantis

	/**
	 * Permet de savoir si on a la garantie que la valeur est positive
	 * 
	 * Cette méthode n'a aucune garantie. Si elle renvoie toujours faux, même si la valeur est une constante positive,
	 * elle serait correcte.
	 * 
	 * @return Vrai si la valeur est toujours positive
	 */
	public boolean estGarantiePositive();



}
