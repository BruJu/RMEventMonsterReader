package fr.bruju.rmeventreader.implementation.formulatracker.operateurdesimplification.operation.inclusionglobale.gestionnairedecondition;

import fr.bruju.rmeventreader.implementation.formulatracker.composant.condition.CArme;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.condition.CSwitch;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.condition.CVariable;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.condition.Condition;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.valeur.VConstante;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.visiteur.VisiteurDeComposantsADefaut;

public class CreateurDeGestionnaire implements VisiteurDeComposantsADefaut {

	private GestionnaireDeCondition gestionnaire;

	@Override
	public void comportementParDefaut() {
		gestionnaire = null;
	}

	public GestionnaireDeCondition getGestionnaire(Condition condition) {
		condition.accept(this);
		return gestionnaire;
	}

	@Override
	public void visit(CArme cArme) {
		gestionnaire = new GestionnaireArme(cArme);
	}

	@Override
	public void visit(CSwitch cSwitch) {
		gestionnaire = new GestionnaireSwitch(cSwitch);
	}

	@Override
	public void visit(CVariable cVariable) {
		if (!(cVariable.droite instanceof VConstante)) {
			gestionnaire = null;
			return;
		}
		
		switch (cVariable.operateur) {
		case IDENTIQUE:
			gestionnaire = new GestionnaireVariableIdentique(cVariable);
			break;
		case DIFFERENT:
			gestionnaire = new GestionnaireVariableDifferent(cVariable);
			break;
		case INF:
			gestionnaire = new GestionnaireVariableInferieur(cVariable, false);
			break;
		case INFEGAL:
			gestionnaire = new GestionnaireVariableInferieur(cVariable, true);
			break;
		case SUP:
			gestionnaire = new GestionnaireVariableSuperieur(cVariable, false);
			break;
		case SUPEGAL:
			gestionnaire = new GestionnaireVariableSuperieur(cVariable, true);
			break;
		default:
			gestionnaire = null;
		}
	}
}
