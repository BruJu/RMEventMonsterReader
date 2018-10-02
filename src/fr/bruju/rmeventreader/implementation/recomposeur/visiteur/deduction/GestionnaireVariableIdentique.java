package fr.bruju.rmeventreader.implementation.recomposeur.visiteur.deduction;

import fr.bruju.rmeventreader.implementation.recomposeur.composant.condition.Condition;
import fr.bruju.rmeventreader.implementation.recomposeur.composant.condition.ConditionFixe;
import fr.bruju.rmeventreader.implementation.recomposeur.composant.condition.ConditionValeur;
import fr.bruju.rmeventreader.implementation.recomposeur.composant.valeur.Constante;
import fr.bruju.rmeventreader.implementation.recomposeur.composant.valeur.Valeur;

/**
 * Gestionnaire de condition disant qu'une valeur est égale à une constante
 * 
 * @author Bruju
 *
 */
public class GestionnaireVariableIdentique implements GestionnaireDeCondition {
	/** Valeur à gauche */
	private Valeur base;
	/** Valeur à laquelle elle est égale */
	private int maDroite;
	
	/**
	 * Crée un gestionnaire de conditions disant que la valeur à gauche de la condition est égale à la valeur à droite
	 * @param cVariable La condition
	 */
	public GestionnaireVariableIdentique(ConditionValeur cVariable, int valeurDroite) {
		this.base = cVariable.gauche;
		maDroite = valeurDroite;
	}

	@Override
	public Condition conditionVariable(ConditionValeur cond) {
		if(!(base.equals(cond.gauche))) {
			return cond;
		}
		
		Integer saDroite = Constante.evaluer(cond.droite);
		
		if (saDroite == null) {
			return cond;
		}
		
		boolean r = cond.operateur.test(maDroite, saDroite);
		
		return ConditionFixe.get(r);
	}
}
