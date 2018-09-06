package fr.bruju.rmeventreader.actionmakers.composition.visiteur.implementation.deduction;

import fr.bruju.rmeventreader.actionmakers.composition.composant.condition.Condition;
import fr.bruju.rmeventreader.actionmakers.composition.composant.condition.ConditionFixe;
import fr.bruju.rmeventreader.actionmakers.composition.composant.condition.ConditionValeur;
import fr.bruju.rmeventreader.actionmakers.composition.composant.valeur.Constante;
import fr.bruju.rmeventreader.actionmakers.composition.composant.valeur.Valeur;
import fr.bruju.rmeventreader.actionmakers.executeur.modele.Comparateur;

/**
 * Gestionnaire de conditions sur la différence de valeur d'une variable
 * 
 * @author Bruju
 *
 */
public class GestionnaireVariableDifferent implements GestionnaireDeCondition {
	/** Variable testée */
	private Valeur base;
	/** Valeur différente de la variable */
	private int maDroite;

	/**
	 * Crée un gestionnaire de variable disant que la variable à gauche de la condition est différente de la valeur
	 * constante à droite de la condition
	 * 
	 * @param cVariable La condition
	 */
	public GestionnaireVariableDifferent(ConditionValeur cVariable, int valeurDroite) {
		this.base = cVariable.gauche;
		maDroite = valeurDroite;
	}

	@Override
	public Condition conditionVariable(ConditionValeur cond) {
		if (!(base.equals(cond.gauche))
				|| (cond.operateur != Comparateur.IDENTIQUE && cond.operateur != Comparateur.DIFFERENT)) {
			return cond;
		}

		Integer saDroite = Constante.evaluer(cond.droite);
		
		if (saDroite == null) {
			return cond;
		}

		if (cond.operateur == Comparateur.IDENTIQUE) {
			if (maDroite == saDroite.intValue()) {
				return ConditionFixe.get(false);
			}
		}

		if (cond.operateur == Comparateur.DIFFERENT) {
			if (maDroite == saDroite.intValue()) {
				return ConditionFixe.get(true);
			}
		}

		return cond;
	}

}
