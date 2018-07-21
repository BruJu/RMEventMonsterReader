package fr.bruju.rmeventreader.implementation.formulareader.stock;

import java.util.ArrayList;
import java.util.List;

import fr.bruju.rmeventreader.implementation.formulareader.formule.condition.Condition;
import fr.bruju.rmeventreader.implementation.formulareader.formule.valeur.Valeur;
import fr.bruju.rmeventreader.implementation.formulareader.model.PersonnageReel;
import fr.bruju.rmeventreader.utilitaire.Triplet;

/**
 * Représentation d'une formule qui a été déduite du décrypteur de formules
 * 
 * @author Bruju
 *
 */
public class Formule {
	/**
	 * Nom de l'attaque
	 */
	public final String nomAttaque;
	/**
	 * Variables ayant déclenché le stockage de la formule
	 */
	public final List<Integer> variableTouchee;
	/**
	 * Liste des conditions requises
	 */
	public final List<Condition> conditionsRequises;
	/**
	 * Formule
	 */
	public final Valeur formule;

	/* ========================
	 * CONSTRUCTION DE FORMULES
	 * ======================== */

	/**
	 * Crée une formule simple
	 */
	public Formule(String nomAttaque, Integer variableTouchee, List<Condition> conditionsRequises, Valeur formule) {
		this.nomAttaque = nomAttaque;
		this.variableTouchee = new ArrayList<>();
		this.variableTouchee.add(variableTouchee);
		this.conditionsRequises = conditionsRequises;
		this.formule = formule;
	}

	/* ===========
	 * UNIFICATION
	 * =========== */

	/**
	 * Crée une formule unifiée
	 */
	private Formule(String nomAttaque, List<Integer> variableTouchee, List<Condition> conditionsRequises,
			Valeur formule) {
		this.nomAttaque = nomAttaque;
		this.variableTouchee = variableTouchee;
		this.conditionsRequises = conditionsRequises;
		this.formule = formule;
	}

	/**
	 * Crée une nouvelle formule en unifiant cette formule et le triplet v. A l'issue, cette formule n'est plus
	 * utilisable.
	 */
	public Formule unifier(Triplet<Integer, List<Condition>, Valeur> v) {
		String nomAttaque = this.nomAttaque;
		
		List<Integer> variablesTouchees = this.variableTouchee;
		variablesTouchees.add(v.a);
		
		List<Condition> conditions = this.conditionsRequises;

		Valeur valeurUnifiee = this.formule.similariser(v.c);
		
		return new Formule(nomAttaque, variablesTouchees, conditions, valeurUnifiee);
	}

	/* =========
	 * AFFICHAGE
	 * ========= */

	/**
	 * Renvoie une représentation de la formule
	 */
	public String getString() {
		return getString(null);
	}

	/**
	 * Renvoie une représentation de la formule en utilisant la fonction de simplification du personnage donné
	 */
	public String getString(PersonnageReel personnage) {
		StringBuilder sb = new StringBuilder();

		sb.append("{").append(nomAttaque).append("} ").append(variableTouchee).append(" # ");

		conditionsRequises.forEach(cond -> sb.append("<").append(cond.getString()).append("> "));

		sb.append(" = ")
				.append((personnage == null) ? formule.getString() : personnage.subFormula(formule.getString()));

		return sb.toString();
	}
}
