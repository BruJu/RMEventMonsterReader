package fr.bruju.rmeventreader.implementation.recomposeur.composant.valeur;

import fr.bruju.rmeventreader.implementation.recomposeur.composant.visiteur.Visiteur;
import java.util.Objects;

/**
 * Variable dans la base de données.
 * 
 * @author Bruju
 *
 */
public class ValeurEntree implements Valeur {
	/* =========
	 * COMPOSANT
	 * ========= */
	/** Numéro de la variable */
	public final int id;

	/**
	 * Construit une variable ayant le numéro donné
	 * @param id Le numéro de la variable
	 */
	public ValeurEntree(int id) {
		this.id = id;
	}

	/* ================
	 * AFFICHAGE SIMPLE
	 * ================ */

	@Override
	public String toString() {
		return Integer.toString(id);
	}

	/* ========
	 * VISITEUR
	 * ======== */

	@Override
	public void accept(Visiteur visiteur) {
		visiteur.visit(this);
	}

	@Override
	public ValeurEntree simplifier() {
		return this;
	}

	@Override
	public int hashCode() {
		return Objects.hash("VENT", id);
	}

	@Override
	public boolean equals(Object object) {
		if (object instanceof ValeurEntree) {
			ValeurEntree that = (ValeurEntree) object;
			return this.id == that.id;
		}
		return false;
	}
}
