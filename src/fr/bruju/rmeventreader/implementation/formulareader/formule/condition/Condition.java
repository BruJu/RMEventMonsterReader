package fr.bruju.rmeventreader.implementation.formulareader.formule.condition;

import fr.bruju.rmeventreader.implementation.formulareader.formule.DependantDeStatistiquesEvaluation;
import fr.bruju.rmeventreader.implementation.formulareader.formule.NonEvaluableException;
import fr.bruju.rmeventreader.implementation.formulareader.formule.valeur.Valeur;
import fr.bruju.rmeventreader.rmdatabase.Affectation;

public interface Condition {
	
	public String getString();
	
	public Condition revert();
	
	public int degreDeSimilitude(Condition autre);
	
	public String getStringApresAutre(Condition autre);
	
	public Valeur estVariableIdentiqueA();
	
	/**
	 * Evalue la condition avec l'affectation donnée
	 * @param affectation L'affectation
	 * @return null si l'affectation ne permet pas de déduire si la condition est respectée ou non, vrai si elle est
	 * respectée, faux sinon.
	 */
	public Boolean resoudre(Affectation affectation);

	
	/**
	 * Renvoie un tableau avec : si la conditon est respectée avec la borne inférieure et si la condition est respectée avec la borne supérieure
	 * @return
	 * @throws NonEvaluableException
	 * @throws DependantDeStatistiquesEvaluation
	 */
	public boolean[] tester() throws NonEvaluableException, DependantDeStatistiquesEvaluation;
	
	public default boolean testerDeterministe() throws NonEvaluableException, DependantDeStatistiquesEvaluation {
		boolean[] test = tester();
		
		if (test[0] == test[1]) {
			return test[0];
		} else {
			throw new NonEvaluableException();
		}
	}
	
	public Condition evaluationPartielle(Affectation affectation);
}