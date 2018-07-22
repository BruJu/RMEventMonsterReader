package fr.bruju.rmeventreader.implementation.formulatracker.formule.formule;

import fr.bruju.rmeventreader.actionmakers.actionner.Operator;
import fr.bruju.rmeventreader.implementation.formulatracker.formule.valeur.Valeur;
import fr.bruju.rmeventreader.utilitaire.Utilitaire;

public class ValeurDegats implements FormuleDeDegats {
	public final Operator operator;
	public final Valeur formule;

	public ValeurDegats(Operator operator, Valeur formule) {
		this.operator = operator;
		this.formule = formule;
	}

	@Override
	public String getString() {
		StringBuilder sb = new StringBuilder();

		sb.append(Utilitaire.getSymbole(operator));
		sb.append(" ");
		sb.append(formule.getString());

		return sb.toString();
	}

	@Override
	public Operator getOperator() {
		return operator;
	}

	@Override
	public Valeur getFormule() {
		return formule;
	}

}
