package fr.bruju.rmeventreader.implementation.formulatracker.formule.formule;

import java.util.List;
import java.util.stream.Collectors;

import fr.bruju.rmeventreader.actionmakers.actionner.Operator;
import fr.bruju.rmeventreader.implementation.formulareader.formule.Utilitaire;
import fr.bruju.rmeventreader.implementation.formulatracker.formule.condition.Condition;
import fr.bruju.rmeventreader.implementation.formulatracker.formule.valeur.Valeur;

public class ConditionVersValeur implements FormuleDeDegats {

	private Operator operator;
	private List<Condition> conditions;
	private Valeur formule;

	public ConditionVersValeur(Operator operator, List<Condition> conditions, Valeur vDroite) {
		this.operator=  operator;
		this.conditions = conditions;
		this.formule = vDroite;
	}

	@Override
	public String getString() {
		StringBuilder sb = new StringBuilder();
		
		sb.append(Utilitaire.getSymbole(operator));
		sb.append(" [");
		
		sb.append(conditions.stream().map(c -> c.getString()).collect(Collectors.joining(" ")));
		
		sb.append("] ⇒ ");
		sb.append(formule.getString());
		
		return sb.toString();
	}

}
