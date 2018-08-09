package fr.bruju.rmeventreader.actionmakers.composition.composant.valeur;

import fr.bruju.rmeventreader.actionmakers.composition.composant.ElementFeuille;
import fr.bruju.rmeventreader.actionmakers.composition.composant.operation.Affectation;
import fr.bruju.rmeventreader.actionmakers.composition.composant.operation.Operation;
import fr.bruju.rmeventreader.actionmakers.composition.visiteur.template.Visiteur;

import java.util.Objects;

/**
 * Variable dans la base de données.
 * 
 * @author Bruju
 *
 */
public class Entree implements Valeur, ElementFeuille {
	/* =========
	 * COMPOSANT
	 * ========= */
	/** Numéro de la variable */
	public final int id;

	/**
	 * Construit une variable ayant le numéro donné
	 * @param id Le numéro de la variable
	 */
	public Entree(int id) {
		this.id = id;
	}

	/* ================
	 * AFFICHAGE SIMPLE
	 * ================ */

	@Override
	public String toString() {
		return "V[" + Integer.toString(id) + "]";
	}

	/* ========
	 * VISITEUR
	 * ======== */

	@Override
	public void accept(Visiteur visiteur) {
		visiteur.visit(this);
	}

	@Override
	public Entree simplifier() {
		return this;
	}

	@Override
	public int hashCode() {
		return Objects.hash("VENT", id);
	}

	@Override
	public boolean equals(Object object) {
		if (object instanceof Entree) {
			Entree that = (Entree) object;
			return this.id == that.id;
		}
		return false;
	}

	@Override
	public Algorithme toAlgorithme() {
		return new Algorithme(this);
	}

	public static Integer extraire(Valeur v) {
		if (v instanceof Entree) {
			return ((Entree) v).id;
		}

		if (v instanceof Algorithme) {
			Algorithme a = (Algorithme) v;

			if (a.composants.isEmpty()) {
				return null;
			}

			// Affectation initiale

			Integer valeurNumerique = null;

			for (Operation c : a.composants) {

				if (c instanceof Affectation) {
					Affectation aff = (Affectation) c;

					if (aff.base instanceof Entree) {
						valeurNumerique = ((Entree) aff.base).id;
					} else {
						return null;
					}
				} else {
					return null;
				}
			}

			return valeurNumerique;
		}
		return null;
	}
}
