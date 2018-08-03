package fr.bruju.rmeventreader.implementation.recomposeur.composant.condition;

import java.util.Objects;

import fr.bruju.rmeventreader.actionmakers.actionner.Operator;
import fr.bruju.rmeventreader.implementation.recomposeur.composant.valeur.Valeur;
import fr.bruju.rmeventreader.implementation.recomposeur.composant.visiteur.Visiteur;
import fr.bruju.rmeventreader.utilitaire.Utilitaire;

/**
 * Condition portant sur une variable
 * 
 * @author Bruju
 *
 */
public class ConditionValeur implements Condition {
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
}
