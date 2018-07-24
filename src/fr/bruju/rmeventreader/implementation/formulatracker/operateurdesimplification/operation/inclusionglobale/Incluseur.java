package fr.bruju.rmeventreader.implementation.formulatracker.operateurdesimplification.operation.inclusionglobale;

import java.util.List;
import java.util.stream.Collectors;

import fr.bruju.rmeventreader.actionmakers.actionner.Operator;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.condition.Condition;
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
		List<Condition> conditions = formuleBase.conditions.stream().collect(Collectors.toList());
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
		
		// Formule de dégâts jamais explorée
		if (formule == null) {
			return null;
		}
		
		// Retour
		Operator operateur = formuleBase.operator;
		return new FormuleDeDegats(operateur, conditions, formule);
	}
}
