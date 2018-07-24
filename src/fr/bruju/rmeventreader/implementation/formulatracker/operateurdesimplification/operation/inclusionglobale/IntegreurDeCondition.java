package fr.bruju.rmeventreader.implementation.formulatracker.operateurdesimplification.operation.inclusionglobale;

import fr.bruju.rmeventreader.implementation.formulatracker.composant.Composant;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.condition.CArme;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.condition.CSwitch;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.condition.CVariable;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.condition.Condition;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.visiteur.ConstructeurDeComposant;
import fr.bruju.rmeventreader.implementation.formulatracker.operateurdesimplification.operation.inclusionglobale.gestionnairedecondition.CreateurDeGestionnaire;
import fr.bruju.rmeventreader.implementation.formulatracker.operateurdesimplification.operation.inclusionglobale.gestionnairedecondition.GestionnaireDeCondition;

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
