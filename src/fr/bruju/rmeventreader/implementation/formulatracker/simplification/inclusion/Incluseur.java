package fr.bruju.rmeventreader.implementation.formulatracker.simplification.inclusion;

import java.util.List;

import fr.bruju.rmeventreader.actionmakers.actionner.Operator;
import fr.bruju.rmeventreader.implementation.formulatracker.formule.condition.Condition;
import fr.bruju.rmeventreader.implementation.formulatracker.formule.formule.ConditionVersValeur;
import fr.bruju.rmeventreader.implementation.formulatracker.formule.formule.ValeurDegats;
import fr.bruju.rmeventreader.implementation.formulatracker.formule.valeur.VTernaire;
import fr.bruju.rmeventreader.implementation.formulatracker.formule.valeur.Valeur;

/**
 * Cette classe a pour but de transformer les formules de la classe ConditionVersValeur vers ValeurDegats
 * 
 * @author Bruju
 *
 */
public class Incluseur {
	
	public ValeurDegats inclure(ConditionVersValeur conditionValeur) {
		Operator operateur = conditionValeur.operator;
		
		List<Condition> conditions = conditionValeur.conditions;
		Valeur formuleBase = conditionValeur.formule;
		
		IntegreurDeCondition integreur = new IntegreurDeCondition();
		Valeur v = formuleBase;
		
		for (int i = conditions.size() - 1 ; i >= 0 ; i --) {
			Condition c = conditions.get(i);
			
			v = integreur.integrerCondition(c, v);
			
			if (v == null) {
				return null;
			}
			
			v = new VTernaire(c, v, null);
		}
		
		return new ValeurDegats(operateur, v);
	}
	
	

}
