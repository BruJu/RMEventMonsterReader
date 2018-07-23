package fr.bruju.rmeventreader.implementation.formulatracker.composant.bouton;

import java.util.Objects;

import fr.bruju.rmeventreader.implementation.formulatracker.composant.visiteur.VisiteurDeComposants;

/**
 * Interrupteur dont la valeur est inconnue
 * 
 * @author Bruju
 *
 */
public class BBase implements Bouton {
	/* =========
	 * COMPOSANT
	 * ========= */
	
	/** Numéro de l'interrupteur */
	public final int numero;

	/**
	 * Construit un interrupteur avec le numéro donné 
	 * @param numero Le numéro de l'interrupteur
	 */
	public BBase(int numero) {
		this.numero = numero;
	}


	/* ================
	 * AFFICHAGE SIMPLE
	 * ================ */

	@Override
	public String getString() {
		return "S[" + numero + "]";
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
		if (!(other instanceof BBase)) {
			return false;
		}
		BBase castOther = (BBase) other;
		return Objects.equals(numero, castOther.numero);
	}

	@Override
	public int hashCode() {
		return Objects.hash(numero) * 1409;
	}

}
