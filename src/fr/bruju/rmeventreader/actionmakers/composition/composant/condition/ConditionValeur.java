package fr.bruju.rmeventreader.actionmakers.composition.composant.condition;

import java.util.Objects;

import fr.bruju.rmeventreader.actionmakers.actionner.Operator;
import fr.bruju.rmeventreader.actionmakers.composition.composant.Element;
import fr.bruju.rmeventreader.actionmakers.composition.composant.ElementIntermediaire;
import fr.bruju.rmeventreader.actionmakers.composition.composant.valeur.Constante;
import fr.bruju.rmeventreader.actionmakers.composition.composant.valeur.Valeur;
import fr.bruju.rmeventreader.actionmakers.composition.visiteur.template.Visiteur;
import fr.bruju.rmeventreader.utilitaire.Utilitaire;

/**
 * Condition portant sur une variable
 * 
 * @author Bruju
 *
 */
public class ConditionValeur implements Condition, ElementIntermediaire {
	/* =========
	 * COMPOSANT
	 * ========= */

	/** Opérande de gauche */
	public final Valeur gauche;
	/** Opérateur de comparaison */
	public final Operator operateur;
	/** Opérande de droite */
	public final Valeur droite;

	/**
	 * Construit une condition portant sur des valeurs
	 * 
	 * @param gauche Opérande de gauche
	 * @param operateur Opérateur de test
	 * @param vDroite Opérande de droite
	 */
	public ConditionValeur(Valeur gauche, Operator operateur, Valeur vDroite) {
		this.gauche = gauche;
		this.operateur = operateur;
		this.droite = vDroite;
	}

	public ConditionValeur(Valeur interrupteur, boolean valeur) {
		this.gauche = interrupteur;
		
		if (valeur) {
			this.operateur = Operator.IDENTIQUE;
		} else {
			this.operateur = Operator.DIFFERENT;
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
		return gauche.toString() + " " + Utilitaire.getSymbole(operateur) + " " + droite.toString();
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
		if (gauche instanceof Constante && droite instanceof Constante) {
			int gInt = ((Constante) gauche).valeur;
			int dInt = ((Constante) droite).valeur;
			
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
	public ElementIntermediaire fonctionDeRecreation(Element[] elementsFils) {
		return new ConditionValeur((Valeur) elementsFils[0], this.operateur, (Valeur) elementsFils[1]);
	}
}
