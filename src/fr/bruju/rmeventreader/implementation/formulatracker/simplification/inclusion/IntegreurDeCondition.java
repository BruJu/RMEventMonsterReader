package fr.bruju.rmeventreader.implementation.formulatracker.simplification.inclusion;

import fr.bruju.rmeventreader.implementation.formulatracker.formule.Composant;
import fr.bruju.rmeventreader.implementation.formulatracker.formule.condition.CArme;
import fr.bruju.rmeventreader.implementation.formulatracker.formule.condition.CSwitch;
import fr.bruju.rmeventreader.implementation.formulatracker.formule.condition.CVariable;
import fr.bruju.rmeventreader.implementation.formulatracker.formule.condition.Condition;
import fr.bruju.rmeventreader.implementation.formulatracker.simplification.ConstructeurDeComposant;
import fr.bruju.rmeventreader.implementation.formulatracker.simplification.inclusion.gestionnairesdeconditions.CreateurDeGestionnaire;
import fr.bruju.rmeventreader.implementation.formulatracker.simplification.inclusion.gestionnairesdeconditions.GestionnaireDeCondition;

public class IntegreurDeCondition extends ConstructeurDeComposant implements Integreur {	
	private GestionnaireDeCondition gestionnaire;
	
	public IntegreurDeCondition() {
		super();
	}
	
	public Composant integrerCondition(Condition condition, Composant valeur) {
		gestionnaire = new CreateurDeGestionnaire().getGestionnaire(this, condition);
		if (gestionnaire == null) {
			return valeur;
		}
		
		valeur.accept(this);
		return pile.pop();
	}
	
	@Override
	public void gestionnairePush(Condition condition, boolean reponse) {
		pile.push(condition);
		conditionFlag = reponse;
	}

	@Override
	public void visit(CArme cArme) {
		gestionnaire.conditionArme(cArme);
	}

	@Override
	public void visit(CSwitch cSwitch) {
		gestionnaire.conditionSwitch(cSwitch);
	}

	@Override
	public void visit(CVariable cVariable) {
		gestionnaire.conditionVariable(cVariable);
	}

	@Override
	public void refuse(CArme cArme) {
		super.visit(cArme);
	}

	@Override
	public void refuse(CSwitch cSwitch) {
		super.visit(cSwitch);
	}
	
	@Override
	public void refuse(CVariable cVariable) {
		super.visit(cVariable);
	}
}
