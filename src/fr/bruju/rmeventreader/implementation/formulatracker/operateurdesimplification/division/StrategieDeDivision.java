package fr.bruju.rmeventreader.implementation.formulatracker.operateurdesimplification.division;

import java.util.List;
import java.util.Set;
import java.util.function.Function;

import fr.bruju.rmeventreader.implementation.formulatracker.composant.condition.Condition;
import fr.bruju.rmeventreader.implementation.formulatracker.operateurdesimplification.inclusion.gestionnairedecondition.GestionnaireDeCondition;

/**
 * Stratégie de division, consistant à fournir un extracteur de conditions, et une fonction générant des gestionnaires.
 * 
 * @author Bruju
 *
 */
public interface StrategieDeDivision {
	/**
	 * Donne un extractuer de conditions pour cette stratégie de division
	 * @return L'extracteur de conditions
	 */
	Extracteur getExtracteur();
	
	/**
	 * Fonction de génération des gestionnaires
	 * @param condition La condition de l'ensemble qui souhaite générer des gestionnaires
	 * @param conditions L'ensemble des conditions extraites
	 * @return La liste des gestionnaires pour respecter la première condition
	 */
	List<GestionnaireDeCondition> getGestionnaires(Condition condition, Set<Condition> conditions);

	public Function<Condition, String> getFonctionDAffichage();

}
