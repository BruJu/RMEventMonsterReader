package fr.bruju.rmeventreader.implementation.formulatracker.operateurdesimplification.inclusion.gestionnairedecondition;

import fr.bruju.rmdechiffreur.modele.Comparateur;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.condition.CFixe;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.condition.CVariable;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.condition.Condition;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.valeur.VConstante;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.valeur.Valeur;

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
	 * @param cVariable La condition
	 */
	public GestionnaireVariableDifferent(CVariable cVariable) {
		this.base = cVariable.gauche;
		maDroite = VConstante.evaluer(cVariable.droite);
	}

	@Override
	public Condition conditionVariable(CVariable cond) {
		if(!(base.equals(cond.gauche)
				&& cond.droite instanceof VConstante)) {
			return cond;
		}
		
		if (cond.operateur != Comparateur.IDENTIQUE && cond.operateur != Comparateur.DIFFERENT) {
			return cond;
		}
		
		int saDroite = VConstante.evaluer(cond.droite);
		
		if (cond.operateur == Comparateur.IDENTIQUE) {
			if (maDroite == saDroite) {
				return CFixe.get(false);
			}
		}
		
		if (cond.operateur == Comparateur.DIFFERENT) {
			if (maDroite == saDroite) {
				return CFixe.get(true);
			}
		}

		return cond;
	}

}
