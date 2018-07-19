package fr.bruju.rmeventreader.implementation.formulareader.formule.condition;

import fr.bruju.rmeventreader.implementation.formulareader.formule.DependantDeStatistiquesEvaluation;
import fr.bruju.rmeventreader.implementation.formulareader.formule.NonEvaluableException;
import fr.bruju.rmeventreader.rmdatabase.Affectation;

/**
 * Condition sur des valeurs
 * @author Bruju
 *
 */
public interface Condition {
	
	/**
	 * Donne une représentation comprérensible par un humain de la condition
	 * @return
	 */
	public String getString();
	
	/**
	 * Renvoie la même condition mais inversée
	 * @return La condition inversée
	 */
	public Condition revert();
	
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
	
	/**
	 * Teste la condition dans le cas minimum et le cas maximum
	 * @return Vrai ou faux si la condition est toujours respectée ou non
	 * @throws NonEvaluableException Si la condition ne peut pas être évaluée avec certitude
	 * @throws DependantDeStatistiquesEvaluation Si la condition dépend de statistiques
	 */
	public default boolean testerDeterministe() throws NonEvaluableException, DependantDeStatistiquesEvaluation {
		boolean[] test = tester();
		
		if (test[0] == test[1]) {
			return test[0];
		} else {
			throw new NonEvaluableException();
		}
	}
	
	/**
	 * Transforme la condition pour avoir une condition prenant compte de l'affecation donnée
	 * @param affectation L'affectation
	 * @return La condition en prenant en compte les données de l'affectation
	 */
	public Condition evaluationPartielle(Affectation affectation);
}