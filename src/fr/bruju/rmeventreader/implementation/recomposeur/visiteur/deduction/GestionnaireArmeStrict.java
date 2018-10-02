package fr.bruju.rmeventreader.implementation.recomposeur.visiteur.deduction;

import fr.bruju.rmeventreader.implementation.recomposeur.composant.condition.Condition;
import fr.bruju.rmeventreader.implementation.recomposeur.composant.condition.ConditionArme;
import fr.bruju.rmeventreader.implementation.recomposeur.composant.condition.ConditionFixe;

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
	public GestionnaireArmeStrict(ConditionArme base) {
		if (!base.haveToOwn) {
			throw new UnsupportedOperationException(
					"Création d'un gestionnaire d'arme strict avec le non port d'un équipement");
		}
		
		heros = base.heros;
		arme = base.objet;
	}

	@Override
	public Condition conditionArme(ConditionArme cArme) {
		if (cArme.heros != heros) {
			return cArme;
		}
		
		if (arme == 0) { // On a pas l'arme demandée
			return ConditionFixe.get(!cArme.haveToOwn);
		} else {
			boolean r = arme == cArme.objet;
			
			if (!cArme.haveToOwn)
				r = !r;
			
			return ConditionFixe.get(r);
		}
	}
}
