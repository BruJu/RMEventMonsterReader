package fr.bruju.rmeventreader.implementation.formulatracker.modifmodifstat.diviseurs;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import fr.bruju.rmeventreader.implementation.formulatracker.composant.condition.Condition;
import fr.bruju.rmeventreader.implementation.formulatracker.formule.attaques.FormuleDeDegats;
import fr.bruju.rmeventreader.implementation.formulatracker.operateurdesimplification.inclusion.IntegreurGeneral;
import fr.bruju.rmeventreader.implementation.formulatracker.operateurdesimplification.inclusion.gestionnairedecondition.GestionnaireDeCondition;
import fr.bruju.rmeventreader.utilitaire.Pair;

public class Diviseur {
	private StrategieDeDivision strategie;

	public Diviseur(StrategieDeDivision strategie) {
		this.strategie = strategie;
	}
	
	public List<Pair<Condition, FormuleDeDegats>> diviser(FormuleDeDegats formule) {
		Set<Condition> conditions = new HashSet<>();
		
		formule.conditions.forEach(condition -> strategie.getExtracteur().extraire(condition, conditions));
		
		if (conditions.isEmpty()) {
			ArrayList<Pair<Condition, FormuleDeDegats>> listeReponse = new ArrayList<>();
			listeReponse.add(new Pair<>(null, formule));
			return listeReponse;
		} else {
			return conditions.stream()
					  .map(condition -> integrer(conditions, condition, formule))
					  .collect(Collectors.toList());
		}
	}

	private Pair<Condition, FormuleDeDegats> integrer(Set<Condition> conditions, Condition condition, FormuleDeDegats formule) {
		List<GestionnaireDeCondition> gestionnaires = strategie.getGestionnaires(condition, conditions);
		
		IntegreurGeneral integreur = new IntegreurGeneral();
		
		integreur.ajouterGestionnaires(gestionnaires);
		
		return new Pair<>(condition, integreur.integrer(formule));
	}
}
