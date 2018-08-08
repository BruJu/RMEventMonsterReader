package fr.bruju.rmeventreader.actionmakers.composition.visiteur.implementation.deduction;

import java.util.Stack;
import java.util.function.BiFunction;

import fr.bruju.rmeventreader.actionmakers.composition.composant.condition.Condition;
import fr.bruju.rmeventreader.actionmakers.composition.composant.condition.ConditionArme;
import fr.bruju.rmeventreader.actionmakers.composition.composant.condition.ConditionFixe;
import fr.bruju.rmeventreader.actionmakers.composition.composant.condition.ConditionValeur;
import fr.bruju.rmeventreader.actionmakers.composition.composant.valeur.Valeur;
import fr.bruju.rmeventreader.actionmakers.composition.visiteur.template.VisiteurConstructeur;

public class Deducteur extends VisiteurConstructeur {
	private Stack<GestionnaireDeCondition> gestionnaires = new Stack<>();
	
	@Override
	protected void avantDeTraiter(Condition c) {
		gestionnaires.push(new CreateurDeGestionnaire().getGestionnaire(c));
	}

	@Override
	protected void finDeTraitement() {
		gestionnaires.pop();
	}

	@Override
	protected Condition modifier(ConditionValeur element) {
		Valeur gauche = (Valeur) traiter(element.gauche);
		Valeur droite = (Valeur) traiter(element.droite);
		
		if (gauche == null || droite == null)
			return null;
		
		gauche = gauche.simplifier();
		droite = droite.simplifier();
		
		if (gauche != element.gauche || droite != element.droite) {
			element = new ConditionValeur(gauche, element.operateur, droite);
		}
		
		return traiterCondition(element, (g, c) -> g.conditionVariable(c));
	}
	
	@Override
	protected Condition modifier(ConditionArme element) {
		return traiterCondition(element, (g, c) -> g.conditionArme(c));
	}

	@SuppressWarnings("unchecked")
	private <COND extends Condition> Condition traiterCondition(COND condition,
			BiFunction<GestionnaireDeCondition, COND, Condition> funcTraitement) {
		
		COND condActuelle = condition;

		for (GestionnaireDeCondition gestionnaire : gestionnaires) {
			if (gestionnaire == null) {
				continue;
			}
			
			Condition condRecue = funcTraitement.apply(gestionnaire, condActuelle);
			
			if (condRecue instanceof ConditionFixe) {
				return condRecue;
			}
			
			condActuelle = (COND) condRecue;
		}
		
		return condActuelle;
	}



}