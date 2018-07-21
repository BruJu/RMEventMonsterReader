package fr.bruju.rmeventreader.implementation.formulareader.stock;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import fr.bruju.rmeventreader.implementation.formulareader.formule.condition.Condition;
import fr.bruju.rmeventreader.implementation.formulareader.formule.valeur.Valeur;
import fr.bruju.rmeventreader.implementation.formulareader.model.PersonnageReel;

public class Formule {
	public final String nomAttaque;
	public final List<Integer> variableTouchee;
	public final List<Condition> conditionsRequises;
	public final Valeur formule;

	public Formule(String nomAttaque, Integer variableTouchee, List<Condition> conditionsRequises, Valeur formule) {
		this.nomAttaque = nomAttaque;
		this.variableTouchee = new ArrayList<>();
		this.variableTouchee.add(variableTouchee);
		this.conditionsRequises = conditionsRequises;
		this.formule = formule;
	}

	public Formule(String nomAttaque, List<Integer> variableTouchee, List<Condition> conditionsRequises,
			Valeur formule) {
		this.nomAttaque = nomAttaque;
		this.variableTouchee = variableTouchee.stream().collect(Collectors.toList());
		this.conditionsRequises = conditionsRequises;
		this.formule = formule;
	}

	public String getString() {
		return getString(null);
	}

	public String getString(PersonnageReel personnage) {
		StringBuilder sb = new StringBuilder();

		sb.append("{").append(nomAttaque).append("} ").append(variableTouchee).append(" # ");

		conditionsRequises.forEach(cond -> sb.append("<").append(cond.getString()).append("> "));

		sb.append(" = ")
				.append((personnage == null) ? formule.getString() : personnage.subFormula(formule.getString()));

		return sb.toString();
	}
}
