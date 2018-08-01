package fr.bruju.rmeventreader.implementation.formulatracker.modifmodifstat.diviseurs;

import java.util.List;

import fr.bruju.rmeventreader.implementation.formulatracker.composant.condition.Condition;
import fr.bruju.rmeventreader.implementation.formulatracker.formule.attaques.FormuleDeDegats;
import fr.bruju.rmeventreader.implementation.formulatracker.operateurdesimplification.inclusion.gestionnairedecondition.GestionnaireDeCondition;

public interface StrategieDeDivision {

	List<Condition> extraireConditions(FormuleDeDegats formule);

	List<GestionnaireDeCondition> getGestionnaires(Condition condition, List<Condition> conditions);

}
