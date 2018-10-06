package fr.bruju.rmeventreader.implementation.detectiondeformules.formulatracker.formule.attaques;

import java.util.List;
import java.util.stream.Collectors;

import fr.bruju.rmeventreader.implementation.detectiondeformules.formulatracker.composant.condition.Condition;
import fr.bruju.rmeventreader.implementation.detectiondeformules.formulatracker.composant.valeur.Valeur;

/**
 * Représenté une formule de dégâts avec des conditions pour appliquer cette formule et la formule en elle-même
 * 
 * @author Bruju
 *
 */
public class FormuleDeDegats {
	/** Conditions pour appliquer cette formule */
	public final List<Condition> conditions;
	/** Quantité de dégâts infligés */
	public final Valeur formule;

	/**
	 * Construit une formule de dégâts
	 * @param conditions Conditions d'application
	 * @param valeur La formule de dégâts
	 */
	public FormuleDeDegats(List<Condition> conditions, Valeur valeur) {
		this.conditions = conditions;
		this.formule = valeur;
	}

	/**
	 * Renvoie une représentation basique de la formule de dégâts
	 * @return Une représentation basique de la formule de dégâts
	 */
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
}
