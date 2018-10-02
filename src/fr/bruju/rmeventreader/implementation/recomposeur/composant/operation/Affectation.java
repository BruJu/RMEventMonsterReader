package fr.bruju.rmeventreader.implementation.recomposeur.composant.operation;

import java.util.List;
import java.util.Objects;

import fr.bruju.rmeventreader.implementation.recomposeur.composant.Element;
import fr.bruju.rmeventreader.implementation.recomposeur.composant.ElementIntermediaire;
import fr.bruju.rmeventreader.implementation.recomposeur.composant.valeur.Valeur;
import fr.bruju.rmeventreader.implementation.recomposeur.visiteur.template.Visiteur;

/**
 * (Re-)Initialise la valeur actuelle
 * 
 * @author Bruju
 *
 * @param <T> Le type de la case mémoire
 */
public final class Affectation implements Operation, ElementIntermediaire {

	/* =========
	 * COMPOSANT
	 * ========= */
	/** Valeur affectée */
	public final Valeur base;

	/**
	 * Affecte cette valeur variadique avec base
	 * 
	 * @param base La valeur de base
	 */
	public Affectation(Valeur base) {
		this.base = base;
	}
	
	/* ================
	 * IMPLEMENTATIONS
	 * ================ */

	@Override
	public String toString() {
		return "|-> " + base.toString();
	}

	@Override
	public boolean cumuler(List<Operation> nouveauxComposants) {
		boolean wasEmpty = nouveauxComposants.isEmpty();
		
		nouveauxComposants.clear();
		nouveauxComposants.add(this);
		
		return !wasEmpty;
	}

	/* ========
	 * VISITEUR
	 * ======== */

	@Override
	public void accept(Visiteur visiteur) {
		visiteur.visit(this);
	}

	@Override
	public Affectation simplifier() {
		return this;
	}

	/* =================
	 * NOEUD AYANT DES FILS
	 * ================= */

	@Override
	public Element[] getFils() {
		return new Element[] { this.base };
	}

	@Override
	public ElementIntermediaire fonctionDeRecreation(Element[] elementsFils) {
		return new Affectation((Valeur) elementsFils[0]);
	}

	/* =================
	 * EQUALS / HASHCODE
	 * ================= */

	@Override
	public int hashCode() {
		return Objects.hash("AFF", base);
	}

	@Override
	public boolean equals(Object object) {
		if (object instanceof Affectation) {
			Affectation that = (Affectation) object;
			return Objects.equals(this.base, that.base);
		}
		return false;
	}

}
