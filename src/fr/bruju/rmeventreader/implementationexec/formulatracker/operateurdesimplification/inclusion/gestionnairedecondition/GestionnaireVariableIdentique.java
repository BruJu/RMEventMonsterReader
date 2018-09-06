package fr.bruju.rmeventreader.implementationexec.formulatracker.operateurdesimplification.inclusion.gestionnairedecondition;

import fr.bruju.rmeventreader.implementationexec.formulatracker.composant.condition.CFixe;
import fr.bruju.rmeventreader.implementationexec.formulatracker.composant.condition.CVariable;
import fr.bruju.rmeventreader.implementationexec.formulatracker.composant.condition.Condition;
import fr.bruju.rmeventreader.implementationexec.formulatracker.composant.valeur.VConstante;
import fr.bruju.rmeventreader.implementationexec.formulatracker.composant.valeur.Valeur;

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
	public GestionnaireVariableIdentique(CVariable cVariable) {
		this.base = cVariable.gauche;
		maDroite = VConstante.evaluer(cVariable.droite);
	}

	@Override
	public Condition conditionVariable(CVariable cond) {
		if(!(base.equals(cond.gauche) && cond.droite instanceof VConstante)) {
			return cond;
		}
		
		int saDroite = VConstante.evaluer(cond.droite);
		
		boolean r = cond.operateur.test(maDroite, saDroite);
		
		return CFixe.get(r);
	}
}
