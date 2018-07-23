package fr.bruju.rmeventreader.implementation.formulatracker.simplification.inclusion.gestionnairesdeconditions;

import fr.bruju.rmeventreader.implementation.formulatracker.composant.condition.CVariable;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.valeur.VConstante;
import fr.bruju.rmeventreader.implementation.formulatracker.simplification.EvaluateurSimple;
import fr.bruju.rmeventreader.implementation.formulatracker.simplification.inclusion.Integreur;

public class GestionnaireVariableIdentique implements GestionnaireDeCondition {

	private Integreur integreur;
	private CVariable base;
	private int maDroite;
	
	private EvaluateurSimple eval;
	
	public GestionnaireVariableIdentique(Integreur integreur, CVariable cVariable) {
		this.integreur = integreur;
		this.base = cVariable;
		eval = new EvaluateurSimple();
		maDroite = eval.evaluer(base.droite);
	}

	@Override
	public Integreur getIntegreur() {
		return integreur;
	}

	@Override
	public void conditionVariable(CVariable cond) {
		if(!(base.gauche == cond.gauche
				&& cond.droite instanceof VConstante)) {
			integreur.refuse(cond);
			return;
		}
		
		int saDroite = eval.evaluer(cond.droite);
		
		boolean r = cond.operateur.test(maDroite, saDroite);
		
		integreur.gestionnairePush(null, r);
	}
	
	

}