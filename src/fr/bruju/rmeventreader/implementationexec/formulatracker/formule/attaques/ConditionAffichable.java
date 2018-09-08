package fr.bruju.rmeventreader.implementationexec.formulatracker.formule.attaques;

import java.util.function.Function;

import fr.bruju.rmeventreader.implementationexec.formulatracker.composant.condition.Condition;

public class ConditionAffichable {
	public final Condition condition;
	public final Function<Condition, String> fonctionDaffichage;
	
	public ConditionAffichable(Condition condition, Function<Condition, String> fonctionDaffichage) {
		this.condition = condition;
		this.fonctionDaffichage = fonctionDaffichage;
	}

	public String appliquerAffichage() {
		return fonctionDaffichage.apply(condition);
	}
}