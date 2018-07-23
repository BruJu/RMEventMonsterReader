package fr.bruju.rmeventreader.implementation.formulatracker.formule.condition;

import fr.bruju.rmeventreader.actionmakers.actionner.Operator;
import fr.bruju.rmeventreader.implementation.formulatracker.formule.valeur.Valeur;
import fr.bruju.rmeventreader.implementation.formulatracker.simplification.VisiteurDeComposants;
import fr.bruju.rmeventreader.utilitaire.Utilitaire;
import java.util.Objects;

/**
 * Condition portant sur une variable
 * 
 * @author Bruju
 *
 */
public class CVariable implements Condition {
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
	public CVariable(Valeur gauche, Operator operateur, Valeur vDroite) {
		this.gauche = gauche;
		this.operateur = operateur;
		this.droite = vDroite;
	}

	@Override
	public Condition revert() {
		return new CVariable(gauche, operateur.revert(), droite);
	}

	/* ================
	 * AFFICHAGE SIMPLE
	 * ================ */

	@Override
	public String getString() {
		return gauche.getString() + " " + Utilitaire.getSymbole(operateur) + " " + droite.getString();
	}

	/* ========
	 * VISITEUR
	 * ======== */

	@Override
	public void accept(VisiteurDeComposants visiteurDeComposant) {
		visiteurDeComposant.visit(this);
	}

	/* =================
	 * EQUALS / HASHCODE
	 * ================= */

	@Override
	public boolean equals(final Object other) {
		if (!(other instanceof CVariable)) {
			return false;
		}
		CVariable castOther = (CVariable) other;
		return Objects.equals(gauche, castOther.gauche) && Objects.equals(operateur, castOther.operateur)
				&& Objects.equals(droite, castOther.droite);
	}

	@Override
	public int hashCode() {
		return Objects.hash(gauche, operateur, droite);
	}

}
