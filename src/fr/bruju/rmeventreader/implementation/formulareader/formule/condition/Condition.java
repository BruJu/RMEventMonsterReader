package fr.bruju.rmeventreader.implementation.formulareader.formule.condition;

import java.util.Iterator;
import java.util.List;

import fr.bruju.rmeventreader.implementation.formulareader.formule.DependantDeStatistiquesEvaluation;
import fr.bruju.rmeventreader.implementation.formulareader.formule.NonEvaluableException;
import fr.bruju.rmeventreader.rmdatabase.Affectation;
import fr.bruju.rmeventreader.rmdatabase.AffectationFlexible;

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

	
	public void modifierAffectation(AffectationFlexible affectation) throws AffectationNonFaisable;
	
	/**
	 * Transforme la condition afin d'intégrer le fait que les conditions de la liste donnée sont respectées
	 * @param conditions La liste de conditions
	 * @return La condition simplifiée par le fait que les conditions données sont vraies
	 */
	public default Condition integrerConditions(List<Condition> conditions) {
		// Transformation de la liste en iterateur pour avoir une vision [debut : reste de la liste]
		return this.integrerConditions(conditions.iterator());
	}

	/**
	 * Transforme la condition afin d'intégrer le fait que les conditions de l'itérateur sont vraies
	 */
	public default Condition integrerConditions(Iterator<Condition> iterator) {
		if (!iterator.hasNext()) {
			// Plus de condition à traiter
			return this;
		} else {
			// On traite la condition en tête
			Condition nouvelleCondition = integrerCondition(iterator.next());
			// La nouvelle condition doit intégrer les conditions restantes
			return nouvelleCondition.integrerConditions(iterator);
		}
	}
	
	/**
	 * Donne une condition simplifiée par que le fait que la condition à inclure est vrai
	 * @param aInclure La condition à inclure
	 * @return Cette condition, mais où les cas où la condition aInclure n'est pas vérifiée sont exclus
	 */
	public Condition integrerCondition(Condition aInclure);
	
	/**
	 * Implémenté uniquement par les conditions fixes. Si il s'agit d'une condition fixe, donne sa valeur
	 * @return null si la condition n'est pas fixe. Vrai ou faux si c'est une condition fixe.
	 */
	public default Boolean fastEval() {
		return null;
	}
}