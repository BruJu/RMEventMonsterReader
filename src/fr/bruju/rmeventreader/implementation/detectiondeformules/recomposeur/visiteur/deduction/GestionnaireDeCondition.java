package fr.bruju.rmeventreader.implementation.detectiondeformules.recomposeur.visiteur.deduction;

import fr.bruju.rmeventreader.implementation.detectiondeformules.recomposeur.composant.condition.Condition;
import fr.bruju.rmeventreader.implementation.detectiondeformules.recomposeur.composant.condition.ConditionArme;
import fr.bruju.rmeventreader.implementation.detectiondeformules.recomposeur.composant.condition.ConditionValeur;


/**
 * Un gestionnaire de condition transforme des conditions par rapport à une condition qui a généré le gestionnaire.
 * <p>
 * Les méthodes du gestionnaire renvoient :
 * <ul>
 * <li> La condition reçue si le gestionnaire la considère interessante (ie il n'est pas plus précis qu'elle ou elle
 * concerne un autre domaine que lui)</li>
 * <li> CFixe.get(true) si la condition ayant généré le gestionnaire est plus spécifique que la condition donné.
 * Dit autrement, toutes les valeurs vérifiant la condition génératrice vérifient également la condition donnée.</li>
 * <li> CFixe.get(false) si la condition du gestionnaire ne permet jamais de vérifier la condition donnée</li>
 * </ul>
 */
public interface GestionnaireDeCondition {
	/**
	 * Traitement de la condition
	 * 
	 * @see 
	 */
	public default Condition conditionArme(ConditionArme cArme) {
		return cArme;
	}

	/**
	 * Traitement de la condition
	 * 
	 * @see 
	 */
	public default Condition conditionVariable(ConditionValeur cVariable) {
		return cVariable;
	}
}
