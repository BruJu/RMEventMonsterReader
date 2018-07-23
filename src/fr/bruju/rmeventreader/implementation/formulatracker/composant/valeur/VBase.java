package fr.bruju.rmeventreader.implementation.formulatracker.composant.valeur;

import java.util.Objects;

import fr.bruju.rmeventreader.implementation.formulatracker.composant.visiteur.VisiteurDeComposants;

/**
 * Variable dans la base de données.
 * 
 * @author Bruju
 *
 */
public class VBase implements Valeur {	
	/* =========
	 * COMPOSANT
	 * ========= */
	/** Numéro de la variable */
	public final int idVariable;
	
	/**
	 * Construit une variable ayant le numéro donné
	 * @param idVariable Le numéro de la variable
	 */
	public VBase(Integer idVariable) {
		this.idVariable = idVariable;
	}
	
	/* ================
	 * AFFICHAGE SIMPLE
	 * ================ */

	@Override
	public String getString() {
		return "V[" + idVariable + "]";
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
		if (!(other instanceof VBase)) {
			return false;
		}
		VBase castOther = (VBase) other;
		return Objects.equals(idVariable, castOther.idVariable);
	}

	@Override
	public int hashCode() {
		return Objects.hash(idVariable) * 6397;
	}
}
