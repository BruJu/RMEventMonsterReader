package fr.bruju.rmeventreader.implementation.recomposeur.composant.condition;

import java.util.Objects;

import fr.bruju.rmeventreader.actionmakers.modele.Comparateur;
import fr.bruju.rmeventreader.implementation.recomposeur.composant.Element;
import fr.bruju.rmeventreader.implementation.recomposeur.composant.ElementIntermediaire;
import fr.bruju.rmeventreader.implementation.recomposeur.composant.valeur.Constante;
import fr.bruju.rmeventreader.implementation.recomposeur.composant.valeur.Valeur;
import fr.bruju.rmeventreader.implementation.recomposeur.visiteur.template.Visiteur;

/**
 * Condition portant sur une variable
 * 
 * @author Bruju
 *
 */
public final class ConditionValeur implements Condition, ElementIntermediaire {
	/* =========
	 * COMPOSANT
	 * ========= */

	/** Opérande de gauche */
	public final Valeur gauche;
	/** Opérateur de comparaison */
	public final Comparateur operateur;
	/** Opérande de droite */
	public final Valeur droite;

	/**
	 * Construit une condition portant sur des valeurs
	 * 
	 * @param gauche Opérande de gauche
	 * @param operateur Opérateur de test
	 * @param vDroite Opérande de droite
	 */
	public ConditionValeur(Valeur gauche, Comparateur operateur, Valeur vDroite) {
		this.gauche = gauche;
		this.operateur = operateur;
		this.droite = vDroite;
	}

	public ConditionValeur(Valeur interrupteur, boolean valeur) {
		this.gauche = interrupteur;
		
		if (valeur) {
			this.operateur = Comparateur.IDENTIQUE;
		} else {
			this.operateur = Comparateur.DIFFERENT;
		}
		
		this.droite = new Constante(1);
	}

	@Override
	public Condition revert() {
		return new ConditionValeur(gauche, operateur.revert(), droite);
	}

	/* ===============
	 * IMPLEMENTATIONS
	 * =============== */

	@Override
	public String toString() {
		return gauche.toString() + " " + operateur.symbole + " " + droite.toString();
	}

	/* ========
	 * VISITEUR
	 * ======== */

	@Override
	public void accept(Visiteur visiteurDeComposant) {
		visiteurDeComposant.visit(this);
	}

	@Override
	public Condition simplifier() {
		Integer gInt = Constante.evaluer(gauche);
		Integer dInt = Constante.evaluer(droite);
		
		if (gInt != null && dInt != null) {
			return ConditionFixe.get(operateur.test(gInt, dInt));
		} else {
			return this;
		}
	}
	
	
	/* =================
	 * EQUALS / HASHCODE
	 * ================= */

	@Override
	public boolean equals(final Object other) {
		if (!(other instanceof ConditionValeur)) {
			return false;
		}
		ConditionValeur castOther = (ConditionValeur) other;
		return Objects.equals(gauche, castOther.gauche) && Objects.equals(operateur, castOther.operateur)
				&& Objects.equals(droite, castOther.droite);
	}

	@Override
	public int hashCode() {
		return Objects.hash(gauche, operateur, droite);
	}
	
	
	
	/* ====================
	 * NOEUD AYANT DES FILS
	 * ==================== */

	@Override
	public Element[] getFils() {
		return new Element[] { this.gauche, this.droite };
	}

	@Override
	public ConditionValeur fonctionDeRecreation(Element[] elementsFils) {
		return new ConditionValeur((Valeur) elementsFils[0], this.operateur, (Valeur) elementsFils[1]);
	}
}
