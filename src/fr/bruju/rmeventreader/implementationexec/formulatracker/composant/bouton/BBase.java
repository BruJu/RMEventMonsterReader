package fr.bruju.rmeventreader.implementationexec.formulatracker.composant.bouton;

import java.util.Objects;

import fr.bruju.rmeventreader.implementationexec.formulatracker.composant.ComposantFeuille;
import fr.bruju.rmeventreader.implementationexec.formulatracker.composant.visiteur.VisiteurDeComposants;

/**
 * Interrupteur dont la valeur est inconnue
 * 
 * @author Bruju
 *
 */
public class BBase implements Bouton, ComposantFeuille {
	/* =========
	 * COMPOSANT
	 * ========= */
	
	/** Numéro de l'interrupteur */
	public final int numero;
	/** Nom de l'interrupteur */
	public final String nom;

	/**
	 * Construit un interrupteur avec le numéro donné 
	 * @param numero Le numéro de l'interrupteur
	 */
	public BBase(int numero) {
		this(numero, "S[" + numero + "]");
	}

	/**
	 * Construit un interrupteur avec le numéro donné 
	 * @param numero Le numéro de l'interrupteur
	 */
	public BBase(int numero, String nom) {
		this.numero = numero;
		this.nom = nom;
	}

	/* ===============
	 * IMPLEMENTATIONS
	 * =============== */

	@Override
	public String getString() {
		return nom;
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
