package fr.bruju.rmeventreader.implementation.formulatracker.operateurdesimplification.inclusion.gestionnairedecondition;

import fr.bruju.rmeventreader.implementation.formulatracker.composant.condition.CArme;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.condition.CFixe;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.condition.Condition;

/**
 * Gestionnaire de ports d'équipements stricts (les personnages ne peuvent porter qu'un équipement). Les conditions
 * créant ce gestionnaire ne peuvent pas porter sur le fait de ne pas posser d'équipement. Si on souhaite donner une
 * condition sur le non port d'équipement, il faut dire que le personnage porte l'arme 0.
 * 
 * 
 * @author Bruju
 *
 */
public class GestionnaireArmeStrict implements GestionnaireDeCondition {
	/** Numéro du héros */
	private final int heros;
	/** Numéro de l'arme */
	private final int arme; 

	/**
	 * Instancie un gestionnaire de condition sur la condition donnée
	 * 
	 * @param base La condition de base
	 */
	public GestionnaireArmeStrict(CArme base) {
		if (!base.haveToOwn) {
			throw new UnsupportedOperationException(
					"Création d'un gestionnaire d'arme strict avec le non port d'un équipement");
		}
		
		heros = base.heros;
		arme = base.objet;
	}

	@Override
	public Condition conditionArme(CArme cArme) {
		if (cArme.heros != heros) {
			return cArme;
		}
		
		if (arme == 0) { // On a pas l'arme demandée
			return CFixe.get(!cArme.haveToOwn);
		} else {
			boolean r = arme == cArme.objet;
			
			if (!cArme.haveToOwn)
				r = !r;
			
			return CFixe.get(r);
		}
	}
}
