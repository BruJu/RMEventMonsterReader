package fr.bruju.rmeventreader.implementation.recomposeur.composant.operation;

import fr.bruju.rmeventreader.actionmakers.modele.Comparateur;
import fr.bruju.rmeventreader.implementation.recomposeur.composant.Element;
import fr.bruju.rmeventreader.implementation.recomposeur.composant.ElementIntermediaire;
import fr.bruju.rmeventreader.implementation.recomposeur.composant.valeur.Algorithme;
import fr.bruju.rmeventreader.implementation.recomposeur.composant.valeur.Valeur;
import fr.bruju.rmeventreader.implementation.recomposeur.visiteur.template.Visiteur;

import java.util.List;
import java.util.Objects;

/**
 * Filtrage de la valeur selon une condition
 * 
 * @author Bruju
 *
 */
public final class Filtre implements Operation, ElementIntermediaire {
	/* =========
	 * COMPOSANT
	 * ========= */
	/** Opérateur de comparaison */
	public final Comparateur operateur;
	/** Opérande de droite de la comparaison */
	public final Valeur valeurComparaison;
	/** Calculs appliqués si la condition est vraie */
	public final Algorithme valeurFiltrage;

	/**
	 * Construit un filtre
	 * 
	 * @param operateur Opérateur de comparaison
	 * @param valeurComparaison Valeur de comparaison
	 * @param valeurFiltrage Si la valeur vérifie le test Valeur Opérateur valeurComparaison, applique la valeur de
	 *            filtrage
	 */
	public Filtre(Comparateur operateur, Valeur valeurComparaison, Algorithme valeurFiltrage) {
		this.operateur = operateur;
		this.valeurComparaison = valeurComparaison;
		this.valeurFiltrage = valeurFiltrage;
	}

	/* ================
	 * IMPLEMENTATIONS
	 * ================ */

	@Override
	public String toString() {
		return "[Si " + operateur.symbole + " " + valeurComparaison.toString() + " alors "
				+ valeurFiltrage.toString() + "]";
	}

	@Override
	public boolean cumuler(List<Operation> nouveauxComposants) {
		nouveauxComposants.add(this);
		return false;
	}
	
	/* ========
	 * VISITEUR
	 * ======== */

	@Override
	public void accept(Visiteur visiteur) {
		visiteur.visit(this);
	}

	@Override
	public Filtre simplifier() {
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

	
	/* ====================
	 * NOEUD AYANT DES FILS
	 * ==================== */

	@Override
	public Element[] getFils() {
		return new Element[] { this.valeurComparaison, this.valeurFiltrage };
	}

	@Override
	public ElementIntermediaire fonctionDeRecreation(Element[] elementsFils) {
		return new Filtre(this.operateur, (Valeur) elementsFils[0], (Algorithme) elementsFils[1]);
	}
}
