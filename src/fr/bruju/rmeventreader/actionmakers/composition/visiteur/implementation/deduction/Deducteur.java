package fr.bruju.rmeventreader.actionmakers.composition.visiteur.implementation.deduction;

import java.util.Objects;
import java.util.Stack;
import java.util.function.BiFunction;

import fr.bruju.rmeventreader.actionmakers.composition.composant.condition.Condition;
import fr.bruju.rmeventreader.actionmakers.composition.composant.condition.ConditionArme;
import fr.bruju.rmeventreader.actionmakers.composition.composant.condition.ConditionFixe;
import fr.bruju.rmeventreader.actionmakers.composition.composant.condition.ConditionValeur;
import fr.bruju.rmeventreader.actionmakers.composition.composant.operation.Conditionnelle;
import fr.bruju.rmeventreader.actionmakers.composition.composant.valeur.Valeur;
import fr.bruju.rmeventreader.actionmakers.composition.visiteur.template.VisiteurConstructeur;

public class Deducteur extends VisiteurConstructeur {
	private Stack<GestionnaireDeCondition> gestionnaires = new Stack<>();

	@Override
	protected Conditionnelle traiter(Conditionnelle element) {
		// Si A alors b sinon c -> si b = c, on peut considérer que A est vrai
		if (Objects.equals(element.siVrai, element.siFaux)) {
			return super.traiter(new Conditionnelle(ConditionFixe.get(true), element.siVrai, null));
		}

		// Sinon traitement normal
		return super.traiter(element);
	}
	@Override
	protected void avantDeTraiter(Condition c) {
		gestionnaires.push(new CreateurDeGestionnaire().getGestionnaire(c));
	}

	@Override
	protected void finDeTraitement() {
		gestionnaires.pop();
	}

	@Override
	protected Condition traiter(ConditionValeur element) {
		Valeur gauche = (Valeur) traiter(element.gauche).simplifier();
		Valeur droite = (Valeur) traiter(element.droite).simplifier();
		
		if (gauche == null || droite == null)
			return null;
		
		if (gauche != element.gauche || droite != element.droite) {
			element = new ConditionValeur(gauche, element.operateur, droite);
		}
		
		return traiterCondition(element, (g, c) -> g.conditionVariable(c));
	}
	
	@Override
	protected Condition traiter(ConditionArme element) {
		return traiterCondition(element, (g, c) -> g.conditionArme(c));
	}

	@SuppressWarnings("unchecked")
	private <COND extends Condition> Condition traiterCondition(COND condition,
			BiFunction<GestionnaireDeCondition, COND, Condition> funcTraitement) {
		
		COND condActuelle = condition;

		for (GestionnaireDeCondition gestionnaire : gestionnaires) {			
			Condition condRecue = funcTraitement.apply(gestionnaire, condActuelle);
			if (condRecue instanceof ConditionFixe) {
				return condRecue;
			}
			
			condActuelle = (COND) condRecue;
		}
		
		return condActuelle;
	}



}
