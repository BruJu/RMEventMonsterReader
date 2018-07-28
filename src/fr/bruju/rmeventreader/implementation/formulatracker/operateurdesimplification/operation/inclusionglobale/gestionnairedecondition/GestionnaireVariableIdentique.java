package fr.bruju.rmeventreader.implementation.formulatracker.operateurdesimplification.operation.inclusionglobale.gestionnairedecondition;

import fr.bruju.rmeventreader.implementation.formulatracker.composant.condition.CFixe;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.condition.CVariable;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.condition.Condition;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.valeur.VConstante;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.visiteur.EvaluationRapide;

public class GestionnaireVariableIdentique implements GestionnaireDeCondition {

	private CVariable base;
	private int maDroite;
	
	private EvaluationRapide eval;
	
	public GestionnaireVariableIdentique(CVariable cVariable) {
		this.base = cVariable;
		eval = EvaluationRapide.getInstance();
		maDroite = eval.evaluer(base.droite);
	}



	@Override
	public Condition conditionVariable(CVariable cond) {
		if(!(base.gauche.equals(cond.gauche)
				&& cond.droite instanceof VConstante)) {
			return cond;
		}
		
		int saDroite = eval.evaluer(cond.droite);
		
		boolean r = cond.operateur.test(maDroite, saDroite);
		
		return CFixe.get(r);
	}
	
	

}
