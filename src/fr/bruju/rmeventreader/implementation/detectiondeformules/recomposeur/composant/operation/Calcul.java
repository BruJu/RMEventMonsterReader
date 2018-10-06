package fr.bruju.rmeventreader.implementation.detectiondeformules.recomposeur.composant.operation;

import fr.bruju.rmdechiffreur.modele.OpMathematique;
import fr.bruju.rmeventreader.implementation.detectiondeformules.recomposeur.composant.Element;
import fr.bruju.rmeventreader.implementation.detectiondeformules.recomposeur.composant.ElementIntermediaire;
import fr.bruju.rmeventreader.implementation.detectiondeformules.recomposeur.composant.valeur.Constante;
import fr.bruju.rmeventreader.implementation.detectiondeformules.recomposeur.composant.valeur.Valeur;
import fr.bruju.rmeventreader.implementation.detectiondeformules.recomposeur.visiteur.template.Visiteur;

import java.util.List;
import java.util.Objects;

/**
 * Effectue une opération arithmétique avec une autre valeur
 * 
 * @author Bruju
 */
public final class Calcul implements Operation, ElementIntermediaire {
	/* =========
	 * COMPOSANT
	 * ========= */

	/** Opérateur */
	public final OpMathematique operateur;
	/** Opérande de droite */
	public final Valeur droite;

	/**
	 * Construit un calcul à partir de deux valeurs et un opérateur
	 * 
	 * @param operateur Un opérateur dans -, *, /, %
	 * @param droite Valeur de droite
	 */
	public Calcul(OpMathematique operateur, Valeur droite) {
		this.operateur = operateur;
		this.droite = droite;
	}
	
	/* ================
	 * IMPLEMENTATIONS
	 * ================ */

	@Override
	public String toString() {
		return operateur.symbole + " " + droite.toString();
	}

	@Override
	public boolean cumuler(List<Operation> nouveauxComposants) {
		if (this.operateur == OpMathematique.AFFECTATION && Constante.evaluer(droite) == 0) {
			return new Affectation(new Constante(0)).cumuler(nouveauxComposants);
		}
		
		
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
	public Calcul simplifier() {
		return this;
	}
	
	/* ====================
	 * NOEUD AYANT DES FILS
	 * ==================== */

	@Override
	public Element[] getFils() {
		return new Element[] { this.droite };
	}

	@Override
	public ElementIntermediaire fonctionDeRecreation(Element[] elementsFils) {
		return new Calcul(this.operateur, (Valeur) elementsFils[0]);
	}

	/* =================
	 * EQUALS / HASHCODE
	 * ================= */
	
	@Override
	public int hashCode() {
		return Objects.hash(operateur, droite);
	}

	@Override
	public boolean equals(Object object) {
		if (object instanceof Calcul) {
			Calcul that = (Calcul) object;
			return Objects.equals(this.operateur, that.operateur) && Objects.equals(this.droite, that.droite);
		}
		return false;
	}


}
