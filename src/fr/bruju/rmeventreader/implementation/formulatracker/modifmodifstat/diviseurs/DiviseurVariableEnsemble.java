package fr.bruju.rmeventreader.implementation.formulatracker.modifmodifstat.diviseurs;

import java.util.List;
import java.util.Set;

import fr.bruju.rmeventreader.implementation.formulatracker.composant.condition.Condition;
import fr.bruju.rmeventreader.implementation.formulatracker.operateurdesimplification.inclusion.gestionnairedecondition.GestionnaireDeCondition;

public class DiviseurVariableEnsemble implements StrategieDeDivision {

	public DiviseurVariableEnsemble(int i, int[] js) {
	}

	@Override
	public List<GestionnaireDeCondition> getGestionnaires(Condition condition, Set<Condition> conditions) {
		return null;
	}

	@Override
	public Extracteur getExtracteur() {
		return null;
	}

}
