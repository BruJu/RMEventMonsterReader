package fr.bruju.rmeventreader.implementation.recomposeur.composant.composantvariadique;

import fr.bruju.rmeventreader.actionmakers.actionner.Operator;
import fr.bruju.rmeventreader.implementation.recomposeur.composant.Element;
import fr.bruju.rmeventreader.implementation.recomposeur.composant.Variadique;
import fr.bruju.rmeventreader.implementation.recomposeur.composant.valeur.Valeur;
import fr.bruju.rmeventreader.implementation.recomposeur.composant.valeur.ValeurVariadique;
import fr.bruju.rmeventreader.implementation.recomposeur.composant.visiteur.Visiteur;
import fr.bruju.rmeventreader.utilitaire.Utilitaire;

import java.util.List;
import java.util.Objects;

/**
 * Filtrage de la valeur selon une condition
 * 
 * @author Bruju
 *
 */
public class Filtre implements ComposantVariadique<Variadique<Valeur>> {
	/* =========
	 * COMPOSANT
	 * ========= */
	/** Opérateur de comparaison */
	public final Operator operateur;
	/** Opérande de droite de la comparaison */
	public final Valeur valeurComparaison;
	/** Calculs appliqués si la condition est vraie */
	public final ValeurVariadique valeurFiltrage;

	/**
	 * Construit un filtre
	 * 
	 * @param operateur Opérateur de comparaison
	 * @param valeurComparaison Valeur de comparaison
	 * @param valeurFiltrage Si la valeur vérifie le test Valeur Opérateur valeurComparaison, applique la valeur de
	 *            filtrage
	 */
	public Filtre(Operator operateur, Valeur valeurComparaison, ValeurVariadique valeurFiltrage) {
		this.operateur = operateur;
		this.valeurComparaison = valeurComparaison;
		this.valeurFiltrage = valeurFiltrage;
	}

	/* ================
	 * IMPLEMENTATIONS
	 * ================ */

	@Override
	public String toString() {
		return "[Si " + Utilitaire.getSymbole(operateur) + " " + valeurComparaison.toString() + " alors "
				+ valeurFiltrage.toString() + "]";
	}

	/* ========
	 * VISITEUR
	 * ======== */

	@Override
	public void accept(Visiteur visiteur) {
		visiteur.visit(this);
	}

	@Override
	public Element simplifier() {
		return this;
	}

	/* =================
	 * EQUALS / HASHCODE
	 * ================= */

	@Override
	public int hashCode() {
		return Objects.hash(operateur, valeurComparaison, valeurFiltrage);
	}

	@Override
	public boolean equals(Object object) {
		if (object instanceof Filtre) {
			Filtre that = (Filtre) object;
			return Objects.equals(this.operateur, that.operateur)
					&& Objects.equals(this.valeurComparaison, that.valeurComparaison)
					&& Objects.equals(this.valeurFiltrage, that.valeurFiltrage);
		}
		return false;
	}

	@Override
	public boolean cumuler(List<ComposantVariadique<? extends Variadique<Valeur>>> nouveauxComposants) {
		
		return false;
	}

}
