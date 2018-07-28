package fr.bruju.rmeventreader.implementation.formulatracker.composant;

import java.util.Objects;

import fr.bruju.rmeventreader.implementation.formulatracker.composant.condition.CFixe;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.condition.Condition;

/**
 * Composant dont la valeur est déterminée par une condition
 * 
 * @author Bruju
 *
 * @param <T> Le type du composant final lorsque la condition est résolue
 */
public abstract class ComposantTernaire<T extends Composant> implements Composant {
	/* =========
	 * COMPOSANT
	 * ========= */

	/** Condition */
	public final Condition condition;
	/** Valeur si la condition est vérifiée */
	public final T siVrai;
	/** Valeur si la condition n'est pas vérifiée */
	public final T siFaux;

	/**
	 * Crée un composant dont la valeur dépend de la condition
	 * @param condition La condition
	 * @param v1 La valeur si la condition est vérifiée
	 * @param v2 La valeur si la condition n'est pas vérifiée
	 */
	public ComposantTernaire(Condition condition, T v1, T v2) {
		this.condition = condition;
		this.siVrai = v1;
		this.siFaux = v2;
	}

	/* ================
	 * AFFICHAGE SIMPLE
	 * ================ */

	@Override
	public String getString() {
		StringBuilder sb = new StringBuilder();
		
		sb.append("[(")
		  .append(condition.getString())
		  .append(") ?")
		  .append(siVrai.getString())
		  .append(" : ")
		  .append(siFaux == null ? "X" : siFaux.getString())
		  .append("]");

		return sb.toString();
	}

	@Override
	public Composant evaluationRapide() {
		Boolean identifier = CFixe.identifier(condition);
		
		if (identifier == null)
			return this;
		
		return identifier ? siVrai : siFaux;
	}
	
	/* =================
	 * EQUALS / HASHCODE
	 * ================= */

	@Override
	public boolean equals(final Object other) {
		if (!(other instanceof ComposantTernaire)) {
			return false;
		}
		ComposantTernaire<?> castOther = (ComposantTernaire<?>) other;
		return Objects.equals(condition, castOther.condition) && Objects.equals(siVrai, castOther.siVrai)
				&& Objects.equals(siFaux, castOther.siFaux);
	}

	@Override
	public int hashCode() {
		return Objects.hash(condition, siVrai, siFaux);
	}
}
