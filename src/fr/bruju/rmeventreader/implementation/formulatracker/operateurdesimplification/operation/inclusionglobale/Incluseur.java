package fr.bruju.rmeventreader.implementation.formulatracker.operateurdesimplification.operation.inclusionglobale;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import fr.bruju.rmeventreader.actionmakers.actionner.Operator;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.condition.Condition;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.valeur.VTernaire;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.valeur.Valeur;
import fr.bruju.rmeventreader.implementation.formulatracker.formule.FormuleDeDegats;

/**
 * Cette classe a pour but de transformer les formules de la classe ConditionVersValeur vers ValeurDegats
 * 
 * @author Bruju
 *
 */
public class Incluseur {
	
	public FormuleDeDegats inclusionGenerale(FormuleDeDegats formuleBase) {
		// Récupération des valeurs
		List<Condition> conditions = new ArrayList<>(); 
		Collections.copy(formuleBase.conditions, conditions);
		Valeur formule = formuleBase.formule;
		IntegreurGeneral integreur = new IntegreurGeneral();
		
		// Integration des conditions
		conditions.sort(new ComparateurCondVar());
		for (Condition condition : conditions) {
			integreur.ajouterCondition(condition);
		}

		// Integration de la valeur
		formule = integreur.integrer(formule);
		conditions = integreur.recupererConditions();
		
		// ???
		if (formule == null) {
			return null;
		}
		
		// Retour
		Operator operateur = formuleBase.operator;
		return new FormuleDeDegats(operateur, conditions, formule);
	}
	
	
	public FormuleDeDegats inclure(FormuleDeDegats conditionValeur) {
		Operator operateur = conditionValeur.operator;
		
		List<Condition> conditions = conditionValeur.conditions;
		Valeur formuleBase = conditionValeur.formule;
		
		IntegreurDeCondition integreur = new IntegreurDeCondition();
		Valeur v = formuleBase;
		
		for (int i = conditions.size() - 1 ; i >= 0 ; i --) {
		//for (int i = 0 ; i != conditions.size(); i ++) {
			Condition c = conditions.get(i);
			
			v = (Valeur) integreur.integrerCondition(c, v);
			
			if (v == null) {
				return null;
			}
			
			v = new VTernaire(c, v, null);
		}
		
		return new FormuleDeDegats(operateur, null, v);
	}
	
	public FormuleDeDegats inclureV(FormuleDeDegats conditionValeur) {
		Operator operateur = conditionValeur.operator;
		
		List<Condition> conditions = conditionValeur.conditions;
		Valeur formuleBase = conditionValeur.formule;
		
		SimplifieurViaIntegration integreur = new SimplifieurViaIntegration();
		Valeur v = formuleBase;

		v = (Valeur) integreur.integerer(formuleBase);

		
		return new FormuleDeDegats(operateur, conditions, v);
	}
	

}
