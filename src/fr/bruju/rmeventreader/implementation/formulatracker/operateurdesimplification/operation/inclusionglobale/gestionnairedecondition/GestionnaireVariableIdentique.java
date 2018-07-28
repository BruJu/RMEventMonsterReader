package fr.bruju.rmeventreader.implementation.formulatracker.operateurdesimplification.operation.inclusionglobale.gestionnairedecondition;

import fr.bruju.rmeventreader.implementation.formulatracker.composant.condition.CFixe;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.condition.CVariable;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.condition.Condition;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.valeur.VConstante;

public class GestionnaireVariableIdentique implements GestionnaireDeCondition {

	private CVariable base;
	private int maDroite;
	
	
	public GestionnaireVariableIdentique(CVariable cVariable) {
		this.base = cVariable;
		maDroite = VConstante.evaluer(base.droite);
	}



	@Override
	public Condition conditionVariable(CVariable cond) {
		if(!(base.gauche.equals(cond.gauche)
				&& cond.droite instanceof VConstante)) {
			return cond;
		}
		
		int saDroite = VConstante.evaluer(cond.droite);
		
		boolean r = cond.operateur.test(maDroite, saDroite);
		
		return CFixe.get(r);
	}
	
	

}
