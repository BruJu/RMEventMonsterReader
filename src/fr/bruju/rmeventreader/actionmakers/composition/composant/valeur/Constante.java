package fr.bruju.rmeventreader.actionmakers.composition.composant.valeur;

import fr.bruju.rmeventreader.actionmakers.composition.composant.ElementFeuille;
import fr.bruju.rmeventreader.actionmakers.composition.composant.operation.Affectation;
import fr.bruju.rmeventreader.actionmakers.composition.composant.operation.Calcul;
import fr.bruju.rmeventreader.actionmakers.composition.composant.operation.Operation;
import fr.bruju.rmeventreader.actionmakers.composition.visiteur.template.Visiteur;

import java.util.Objects;

/**
 * Valeur constante
 * 
 * @author Bruju
 *
 */
public class Constante implements Valeur, ElementFeuille, PasAlgorithme {
	/* =========
	 * COMPOSANT
	 * ========= */

	/** Valeur contenue dans la constante */
	public final int valeur;

	/** Crée une valeur constante */
	public Constante(int valeur) {
		this.valeur = valeur;
	}

	/* ================
	 * AFFICHAGE SIMPLE
	 * ================ */

	@Override
	public String toString() {
		return Integer.toString(valeur);
	}

	/* ========
	 * VISITEUR
	 * ======== */

	@Override
	public void accept(Visiteur visiteur) {
		visiteur.visit(this);
	}

	@Override
	public Constante simplifier() {
		return this;
	}

	/* =================
	 * EQUALS / HASHCODE
	 * ================= */
	@Override
	public int hashCode() {
		return Objects.hash("VCONST", valeur);
	}

	@Override
	public boolean equals(Object object) {
		if (object instanceof Constante) {
			Constante that = (Constante) object;
			return this.valeur == that.valeur;
		}
		return false;
	}

	@Override
	public Algorithme toAlgorithme() {
		return new Algorithme(this);
	}

	public static Integer evaluer(Valeur v) {
		if (v instanceof Constante) {
			return ((Constante) v).valeur;
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

					if (aff.base instanceof Constante) {
						valeurNumerique = ((Constante) aff.base).valeur;
					} else {
						return null;
					}
				} else if (c instanceof Calcul) {
					Calcul calc = (Calcul) c;

					if (calc.droite instanceof Constante) {
						if (valeurNumerique == null) {
							return null;
						}

						valeurNumerique = calc.operateur.compute(valeurNumerique, ((Constante) calc.droite).valeur);
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