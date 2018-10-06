package fr.bruju.rmeventreader.implementation.detectiondeformules.formulatracker.operateurdesimplification.inclusion.gestionnairedecondition;

import fr.bruju.rmeventreader.implementation.detectiondeformules.formulatracker.composant.condition.CFixe;
import fr.bruju.rmeventreader.implementation.detectiondeformules.formulatracker.composant.condition.CSwitch;
import fr.bruju.rmeventreader.implementation.detectiondeformules.formulatracker.composant.condition.Condition;

/**
 * Gestionnaire de conditions pour les conditions sur un interrupteur
 * 
 * @author Bruju
 *
 */
public class GestionnaireSwitch implements GestionnaireDeCondition {
	/** Condition de base */
	private CSwitch base;

	/**
	 * Crée un gestionnaire de conditions pour la condition donnée
	 * @param cSwitch La condition
	 */
	public GestionnaireSwitch(CSwitch cSwitch) {
		this.base = cSwitch;
	}

	@Override
	public Condition conditionSwitch(CSwitch cond) {
		if (!base.interrupteur.equals(cond.interrupteur)) {
			return cond;
		}
		
		return CFixe.get(base.valeur == cond.valeur);
	}
}
