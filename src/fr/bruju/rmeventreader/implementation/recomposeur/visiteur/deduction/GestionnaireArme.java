package fr.bruju.rmeventreader.implementation.recomposeur.visiteur.deduction;

import fr.bruju.rmeventreader.implementation.recomposeur.composant.condition.Condition;
import fr.bruju.rmeventreader.implementation.recomposeur.composant.condition.ConditionArme;
import fr.bruju.rmeventreader.implementation.recomposeur.composant.condition.ConditionFixe;

/**
 * Gestionnaire de conditions sur le port d'un équipement. Ce gestionnaire considère qu'un personnage peut porter
 * plusieurs équipements. Les traitements ne seront donc fait que si le numéro du personnage et le numéro de
 * l'équipement sont identiques entre la condition de base et la condition donnée lors de l'appel à conditionArme.
 * 
 * @author Bruju
 *
 */
public class GestionnaireArme implements GestionnaireDeCondition {
	/** Condition sur le port d'un équipement */
	private ConditionArme base;

	/**
	 * Instancie un gestionnaire de condition sur la condition donnée
	 * 
	 * @param base La condition de base
	 */
	public GestionnaireArme(ConditionArme base) {
		this.base = base;
	}

	@Override
	public Condition conditionArme(ConditionArme cArme) {
		if (cArme.heros != base.heros || cArme.objet != base.objet) {
			return cArme;
		}

		return ConditionFixe.get(base.haveToOwn == cArme.haveToOwn);
	}
}
