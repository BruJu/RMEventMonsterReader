package fr.bruju.rmeventreader.implementation.detectiondeformules.recomposeur.composant.operation;

import java.util.List;
import java.util.Objects;

import fr.bruju.rmeventreader.implementation.detectiondeformules.recomposeur.composant.Element;
import fr.bruju.rmeventreader.implementation.detectiondeformules.recomposeur.composant.ElementIntermediaire;
import fr.bruju.rmeventreader.implementation.detectiondeformules.recomposeur.composant.valeur.Algorithme;
import fr.bruju.rmeventreader.implementation.detectiondeformules.recomposeur.visiteur.template.Visiteur;

public final class SousAlgorithme implements Operation, ElementIntermediaire {
	public final Algorithme algorithme;

	public SousAlgorithme(Algorithme algorithme) {
		this.algorithme = algorithme;
	}

	/* ================
	 * AFFICHAGE SIMPLE
	 * ================ */

	@Override
	public String toString() {
		return "[" + algorithme + "]";
	}

	@Override
	public boolean cumuler(List<Operation> nouveauxComposants) {
		nouveauxComposants.addAll(algorithme.composants);
		
		return true;
	}

	
	/* ========
	 * VISITEUR
	 * ======== */

	@Override
	public void accept(Visiteur visiteur) {
		visiteur.visit(this);
	}

	@Override
	public SousAlgorithme simplifier() {
		return this;
	}


	/* =================
	 * EQUALS / HASHCODE
	 * ================= */
	@Override
	public int hashCode() {
		return Objects.hash("SS", algorithme);
	}

	@Override
	public boolean equals(Object object) {
		if (object instanceof SousAlgorithme) {
			SousAlgorithme that = (SousAlgorithme) object;
			return Objects.equals(this.algorithme, that.algorithme);
		}
		return false;
	}
	
	
	/* ====================
	 * NOEUD AYANT DES FILS
	 * ==================== */

	@Override
	public Element[] getFils() {
		return new Element[] { this.algorithme };
	}

	@Override
	public SousAlgorithme fonctionDeRecreation(Element[] elementsFils) {
		return new SousAlgorithme((Algorithme) elementsFils[0]);
	}
}
