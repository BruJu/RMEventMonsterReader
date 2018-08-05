package fr.bruju.rmeventreader.actionmakers.composition.composant.condition;

import java.util.Objects;

import fr.bruju.rmeventreader.actionmakers.composition.composant.ElementFeuille;
import fr.bruju.rmeventreader.actionmakers.composition.composant.visiteur.Visiteur;

/**
 * Condition sur la possession d'un équipement par un personnage
 * 
 * @author Bruju
 *
 */
public class ConditionArme implements Condition, ElementFeuille {
	/* =========
	 * COMPOSANT
	 * ========= */

	/** Le numéro du héros */
	public final int heros;
	/** Le numéro de l'objet à porter*/
	public final int objet;
	/** Vrai si l'objet doit être porté pour respecter la condition */
	public final boolean haveToOwn;
	
	/**
	 * Construit une condition sur le port d'un objet par un personnage
	 * @param heros Le numéro du personnage
	 * @param objet Le numéro de l'objet
	 */
	public ConditionArme(int heros, int objet) {
		this.heros = heros;
		this.haveToOwn = true;
		this.objet = objet;
	}
	
	/**
	 * Construit une condition sur le port ou non d'un objet par un personnage
	 * @param heros Le numéro du personnage
	 * @param objet Le numéro de l'objet
	 * @param haveToOwn Vrai si l'objet doit être porté, faux sinon
	 */
	private ConditionArme(int heros, int objet, boolean haveToOwn) {
		this.heros = heros;
		this.haveToOwn = haveToOwn;
		this.objet = objet;
	}

	@Override
	public ConditionArme revert() {
		return new ConditionArme(heros, objet, !haveToOwn);
	}
	
	/* ================
	 * AFFICHAGE SIMPLE
	 * ================ */

	@Override
	public String toString() {
		return "Heros #" + heros + " " + ( haveToOwn ? "∋" : "∌") + " Objet " + objet;
	}
	
	/* ========
	 * VISITEUR
	 * ======== */

	@Override
	public void accept(Visiteur visiteurDeComposant) {
		visiteurDeComposant.visit(this);
	}

	@Override
	public ConditionArme simplifier() {
		return this;
	}
	
	/* =================
	 * EQUALS / HASHCODE
	 * ================= */

	@Override
	public boolean equals(final Object other) {
		if (!(other instanceof ConditionArme)) {
			return false;
		}
		ConditionArme castOther = (ConditionArme) other;
		return Objects.equals(heros, castOther.heros) && Objects.equals(objet, castOther.objet)
				&& Objects.equals(haveToOwn, castOther.haveToOwn);
	}

	@Override
	public int hashCode() {
		return Objects.hash(heros, objet, haveToOwn);
	}
}
