package fr.bruju.rmeventreader.implementation.formulareader.formule.valeur;

import java.util.List;
import java.util.function.UnaryOperator;

import fr.bruju.rmeventreader.implementation.formulareader.formule.DependantDeStatistiquesEvaluation;
import fr.bruju.rmeventreader.implementation.formulareader.formule.NonEvaluableException;
import fr.bruju.rmeventreader.implementation.formulareader.formule.condition.Condition;

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
	
	public int[] evaluer() throws NonEvaluableException, DependantDeStatistiquesEvaluation;
	

	public int getPriorite();

	public String getString();
	
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

	public default int evaluerUnique() throws NonEvaluableException, DependantDeStatistiquesEvaluation {
		int[] evaluation = evaluer();
		
		if (evaluation[0] != evaluation[1])
			throw new NonEvaluableException();
		
		return evaluation[0];
	}
	
	// applications récursives
	
	public default Valeur simplifier() {
		return deleguerTraitement(valeur -> valeur.simplifier());
	}

	public default Valeur integrerCondition(List<Condition> aInclure) {
		return deleguerTraitement(valeur -> valeur.integrerCondition(aInclure));
	}
	
	public Valeur deleguerTraitement(UnaryOperator<Valeur> conversion);
	

}
