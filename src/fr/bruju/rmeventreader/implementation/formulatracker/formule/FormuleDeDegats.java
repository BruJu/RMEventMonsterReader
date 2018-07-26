package fr.bruju.rmeventreader.implementation.formulatracker.formule;

import java.util.List;
import java.util.stream.Collectors;

import fr.bruju.rmeventreader.actionmakers.actionner.Operator;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.condition.Condition;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.valeur.Valeur;
import fr.bruju.rmeventreader.utilitaire.Utilitaire;

public class FormuleDeDegats {
	public final List<Condition> conditions;
	public final Valeur formule;

	public FormuleDeDegats(List<Condition> conditions, Valeur vDroite) {
		this.conditions = conditions;
		this.formule = vDroite;
	}

	public String getString() {
		StringBuilder sb = new StringBuilder();

		if (conditions != null) {
			sb.append("[");
			sb.append(conditions.stream().map(c -> c.getString()).collect(Collectors.joining(" ")));
			sb.append("] ");  
		}
		sb.append("=>");
		sb.append(" ");
		sb.append(formule.getString());

		return sb.toString();
	}

	public Valeur getFormule() {
		return formule;
	}

}
