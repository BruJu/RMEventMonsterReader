package fr.bruju.rmeventreader.implementation.formulatracker.modifmodifstat.diviseurs;

import java.util.List;

import fr.bruju.rmeventreader.implementation.formulatracker.composant.condition.Condition;
import fr.bruju.rmeventreader.implementation.formulatracker.formule.attaques.FormuleDeDegats;
import fr.bruju.rmeventreader.implementation.formulatracker.operateurdesimplification.inclusion.gestionnairedecondition.GestionnaireDeCondition;

public class DiviseurArme implements StrategieDeDivision {
	private int numeroPersonnage;
	
	public DiviseurArme(int numeroPersonnage) {
		this.numeroPersonnage = numeroPersonnage;
	}

	@Override
	public List<Condition> extraireConditions(FormuleDeDegats formule) {
		return null;
	}

	@Override
	public List<GestionnaireDeCondition> getGestionnaires(Condition condition, List<Condition> conditions) {
		return null;
	}


}
