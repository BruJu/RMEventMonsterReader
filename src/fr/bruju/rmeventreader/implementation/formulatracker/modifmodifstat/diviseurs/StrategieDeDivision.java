package fr.bruju.rmeventreader.implementation.formulatracker.modifmodifstat.diviseurs;

import java.util.List;
import java.util.Set;

import fr.bruju.rmeventreader.implementation.formulatracker.composant.condition.Condition;
import fr.bruju.rmeventreader.implementation.formulatracker.operateurdesimplification.inclusion.gestionnairedecondition.GestionnaireDeCondition;

public interface StrategieDeDivision {

	List<GestionnaireDeCondition> getGestionnaires(Condition condition, Set<Condition> conditions);

	Extracteur getExtracteur();

}
