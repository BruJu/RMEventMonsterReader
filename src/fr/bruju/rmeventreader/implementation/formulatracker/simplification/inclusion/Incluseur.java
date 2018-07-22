package fr.bruju.rmeventreader.implementation.formulatracker.simplification.inclusion;

import java.util.List;

import fr.bruju.rmeventreader.actionmakers.actionner.Operator;
import fr.bruju.rmeventreader.implementation.formulatracker.formule.condition.Condition;
import fr.bruju.rmeventreader.implementation.formulatracker.formule.formule.ConditionVersValeur;
import fr.bruju.rmeventreader.implementation.formulatracker.formule.formule.ValeurDegats;
import fr.bruju.rmeventreader.implementation.formulatracker.formule.valeur.Valeur;

/**
 * Cette classe a pour but de transformer les formules de la classe ConditionVersValeur vers ValeurDegats
 * 
 * @author Bruju
 *
 */
public class Incluseur {
	
	public Incluseur() {
	}
	
	public ValeurDegats inclure(ConditionVersValeur conditionValeur) {
		Operator operateur = conditionValeur.operator;
		
		List<Condition> conditions = conditionValeur.conditions;
		Valeur formuleBase = conditionValeur.formule;
		
		
		
		
		
		return null;
	}
	
	

}
