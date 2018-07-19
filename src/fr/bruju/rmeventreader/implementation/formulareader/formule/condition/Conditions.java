package fr.bruju.rmeventreader.implementation.formulareader.formule.condition;

import java.util.List;

import fr.bruju.rmeventreader.rmdatabase.Affectation;
import fr.bruju.rmeventreader.rmdatabase.AffectationFlexible;

public interface Conditions {
	public static Affectation convertirEnAffectation(List<Condition> conditions) {
		AffectationFlexible affectation = new AffectationFlexible();
		
		conditions.forEach();
		
		
		return affectation;
	}
}
