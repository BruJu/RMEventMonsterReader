package fr.bruju.rmeventreader.implementation.formulatracker.simplification.inclusion.gestionnairesdeconditions;

import fr.bruju.rmeventreader.implementation.formulatracker.formule.condition.CArme;
import fr.bruju.rmeventreader.implementation.formulatracker.formule.condition.CSwitch;
import fr.bruju.rmeventreader.implementation.formulatracker.formule.condition.CVariable;
import fr.bruju.rmeventreader.implementation.formulatracker.formule.condition.Condition;
import fr.bruju.rmeventreader.implementation.formulatracker.formule.valeur.VConstante;
import fr.bruju.rmeventreader.implementation.formulatracker.simplification.VisiteurDeComposantsADefaut;
import fr.bruju.rmeventreader.implementation.formulatracker.simplification.inclusion.IntegreurDeCondition;

public class CreateurDeGestionnaire implements VisiteurDeComposantsADefaut {

	private IntegreurDeCondition integreur;
	private GestionnaireDeCondition gestionnaire;

	@Override
	public void comportementParDefaut() {
		gestionnaire = null;
	}

	public GestionnaireDeCondition getGestionnaire(IntegreurDeCondition integreurDeCondition, Condition condition) {
		condition.accept(this);
		return gestionnaire;
	}

	@Override
	public void visit(CArme cArme) {
		gestionnaire = new GestionnaireArme(integreur, cArme);
	}

	@Override
	public void visit(CSwitch cSwitch) {
		gestionnaire = new GestionnaireSwitch(integreur, cSwitch);
	}

	@Override
	public void visit(CVariable cVariable) {
		if (!(cVariable.droite instanceof VConstante)) {
			gestionnaire = null;
			return;
		}
		
		switch (cVariable.operateur) {
		case IDENTIQUE:
			gestionnaire = new GestionnaireVariableIdentique(integreur, cVariable);
			break;
		case DIFFERENT:
			gestionnaire = new GestionnaireVariableDifferent(integreur, cVariable);
			break;
		case INF:
			gestionnaire = new GestionnaireVariableInferieur(integreur, cVariable, false);
			break;
		case INFEGAL:
			gestionnaire = new GestionnaireVariableInferieur(integreur, cVariable, true);
			break;
		case SUP:
			gestionnaire = new GestionnaireVariableSuperieur(integreur, cVariable, false);
			break;
		case SUPEGAL:
			gestionnaire = new GestionnaireVariableSuperieur(integreur, cVariable, true);
			break;
		default:
			gestionnaire = null;
		}
	}
}
