package fr.bruju.rmeventreader.implementation.formulatracker.simplification.inclusion;

import fr.bruju.rmeventreader.implementation.formulatracker.composant.condition.CArme;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.condition.CSwitch;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.condition.CVariable;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.condition.Condition;

public interface Integreur {
	void gestionnairePush(Condition condition, boolean reponse);

	void refuse(CArme cArme);

	void refuse(CSwitch cSwitch);

	void refuse(CVariable cVariable);
}