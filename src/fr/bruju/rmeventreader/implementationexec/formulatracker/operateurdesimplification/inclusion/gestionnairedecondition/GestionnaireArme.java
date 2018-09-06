package fr.bruju.rmeventreader.implementationexec.formulatracker.operateurdesimplification.inclusion.gestionnairedecondition;

import fr.bruju.rmeventreader.implementationexec.formulatracker.composant.condition.CArme;
import fr.bruju.rmeventreader.implementationexec.formulatracker.composant.condition.CFixe;
import fr.bruju.rmeventreader.implementationexec.formulatracker.composant.condition.Condition;

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
	private CArme base;

	/**
	 * Instancie un gestionnaire de condition sur la condition donnée
	 * 
	 * @param base La condition de base
	 */
	public GestionnaireArme(CArme base) {
		this.base = base;
	}

	@Override
	public Condition conditionArme(CArme cArme) {
		if (cArme.heros != base.heros || cArme.objet != base.objet) {
			return cArme;
		}

		return CFixe.get(base.haveToOwn == cArme.haveToOwn);
	}
}
