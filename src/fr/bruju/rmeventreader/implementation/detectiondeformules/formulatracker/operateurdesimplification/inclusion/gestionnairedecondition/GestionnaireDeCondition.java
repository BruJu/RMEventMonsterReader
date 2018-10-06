package fr.bruju.rmeventreader.implementation.detectiondeformules.formulatracker.operateurdesimplification.inclusion.gestionnairedecondition;

import fr.bruju.rmeventreader.implementation.detectiondeformules.formulatracker.composant.condition.CArme;
import fr.bruju.rmeventreader.implementation.detectiondeformules.formulatracker.composant.condition.CSwitch;
import fr.bruju.rmeventreader.implementation.detectiondeformules.formulatracker.composant.condition.CVariable;
import fr.bruju.rmeventreader.implementation.detectiondeformules.formulatracker.composant.condition.Condition;

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
	 * @see fr.bruju.rmeventreader.implementation.detectiondeformules.formulatracker.operateurdesimplification.inclusion.gestionnairedecondition.GestionnaireDeCondition
	 */
	public default Condition conditionArme(CArme cArme) {
		return cArme;
	}

	/**
	 * Traitement de la condition
	 * 
	 * @see fr.bruju.rmeventreader.implementation.detectiondeformules.formulatracker.operateurdesimplification.inclusion.gestionnairedecondition.GestionnaireDeCondition
	 */
	public default Condition conditionSwitch(CSwitch cSwitch) {
		return cSwitch;
	}

	/**
	 * Traitement de la condition
	 * 
	 * @see fr.bruju.rmeventreader.implementation.detectiondeformules.formulatracker.operateurdesimplification.inclusion.gestionnairedecondition.GestionnaireDeCondition
	 */
	public default Condition conditionVariable(CVariable cVariable) {
		return cVariable;
	}
}
