package fr.bruju.rmeventreader.implementation.formulatracker.operateurdesimplification.operation.inclusionglobale.gestionnairedecondition;

import fr.bruju.rmeventreader.implementation.formulatracker.composant.Composant;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.condition.CArme;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.condition.CSwitch;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.condition.CVariable;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.condition.Condition;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.valeur.VConstante;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.visiteur.VisiteurRetourneur;

public class CreateurDeGestionnaire extends VisiteurRetourneur<GestionnaireDeCondition> {
	public GestionnaireDeCondition getGestionnaire(Condition condition) {
		return traiter(condition);
	}

	@Override
	protected GestionnaireDeCondition comportementParDefaut(Composant composant) {
		return null;
	}
	
	@Override
	protected GestionnaireDeCondition traiter(CArme cArme) {
		return new GestionnaireArme(cArme);
	}
	
	@Override
	protected GestionnaireDeCondition traiter(CSwitch cSwitch) {
		return new GestionnaireSwitch(cSwitch);
	}

	@Override
	protected GestionnaireDeCondition traiter(CVariable cVariable) {
		if (!(cVariable.droite instanceof VConstante)) {
			return null;
		}

		switch (cVariable.operateur) {
		case IDENTIQUE:
			return new GestionnaireVariableIdentique(cVariable);
		case DIFFERENT:
			return new GestionnaireVariableDifferent(cVariable);
		case INF:
		case INFEGAL:
		case SUP:
		case SUPEGAL:
			return new GestionnaireVariableInegal(cVariable);
		default:
			return null;
		}
	}


}
