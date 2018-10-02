package fr.bruju.rmeventreader.implementation.formulatracker.operateurdesimplification.inclusion.gestionnairedecondition;

import fr.bruju.rmeventreader.implementation.formulatracker.composant.condition.CFixe;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.condition.CVariable;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.condition.Condition;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.valeur.VConstante;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.valeur.Valeur;
import fr.bruju.rmeventreader.implementation.recomposeur.visiteur.deduction.GestionnaireVariableInegal;

/**
 * Gestionnaire de conditions avec une base inégale
 * @author Bruju
 *
 */
public class GestionnaireVariableInegalAdaptateur implements GestionnaireDeCondition {
	// Conditions de type x • constante
	private Valeur base;
	
	
	private GestionnaireVariableInegal gestionnaire;
	

	/**
	 * Crée un gestionnaire de conditions pour la condition d'inégalité donnée
	 * @param cVariable La condition sur laquelle construire le gestionnaire
	 */
	public GestionnaireVariableInegalAdaptateur(CVariable cVariable) {
		base = cVariable.gauche;
		
		gestionnaire = new GestionnaireVariableInegal(cVariable.operateur, VConstante.evaluer(cVariable.droite));
	}


	@Override
	public Condition conditionVariable(CVariable cond) {
		if (!cond.gauche.equals(base)) {
			return cond;
		}
		
		Integer evaluationDroite = VConstante.evaluer(cond.droite);
		
		if (evaluationDroite == null)
			return cond;
		
		Boolean resultat = gestionnaire.traiter(cond.operateur, evaluationDroite);
		
		if (resultat == null)
			return cond;
		else
			return CFixe.get(resultat);
	}

}
