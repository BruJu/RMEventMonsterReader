package fr.bruju.rmeventreader.implementation.formulatracker.composant.condition;

import fr.bruju.rmeventreader.implementation.formulatracker.simplification.VisiteurDeComposants;
import java.util.Objects;

/**
 * Condition sur la possession d'un équipement par un personnage
 * 
 * @author Bruju
 *
 */
public class CArme implements Condition {
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
	public CArme(int heros, int objet) {
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
	private CArme(int heros, int objet, boolean haveToOwn) {
		this.heros = heros;
		this.haveToOwn = haveToOwn;
		this.objet = objet;
	}

	@Override
	public Condition revert() {
		return new CArme(heros, objet, !haveToOwn);
	}

	/* ================
	 * AFFICHAGE SIMPLE
	 * ================ */

	@Override
	public String getString() {
		return "Heros #" + heros + " " + ( haveToOwn ? "∋" : "∌") + " Objet " + objet;
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
		if (!(other instanceof CArme)) {
			return false;
		}
		CArme castOther = (CArme) other;
		return Objects.equals(heros, castOther.heros) && Objects.equals(objet, castOther.objet)
				&& Objects.equals(haveToOwn, castOther.haveToOwn);
	}

	@Override
	public int hashCode() {
		return Objects.hash(heros, objet, haveToOwn);
	}
	
	
}
