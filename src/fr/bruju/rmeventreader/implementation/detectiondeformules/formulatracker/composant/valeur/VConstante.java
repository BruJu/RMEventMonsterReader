package fr.bruju.rmeventreader.implementation.detectiondeformules.formulatracker.composant.valeur;

import java.util.Objects;

import fr.bruju.rmeventreader.implementation.detectiondeformules.formulatracker.composant.Composant;
import fr.bruju.rmeventreader.implementation.detectiondeformules.formulatracker.composant.ComposantFeuille;
import fr.bruju.rmeventreader.implementation.detectiondeformules.formulatracker.composant.visiteur.VisiteurDeComposants;

/**
 * Valeur constante 
 * @author Bruju
 *
 */
public class VConstante implements Valeur, ComposantFeuille {	
	/* =========
	 * COMPOSANT
	 * ========= */
	/** Valeur contenue dans la constante */
	public final int valeur;
	
	/** Crée une valeur constante */
	public VConstante(int valeur) {
		this.valeur = valeur;
	}
	
	/* ================
	 * AFFICHAGE SIMPLE
	 * ================ */

	@Override
	public String getString() {
		return Integer.toString(valeur);
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
		if (!(other instanceof VConstante)) {
			return false;
		}
		VConstante castOther = (VConstante) other;
		return Objects.equals(valeur, castOther.valeur);
	}

	@Override
	public int hashCode() {
		return Objects.hash(valeur) * 8233;
	}
	
	
	/* =================
	 * EVALUATION RAPIDE
	 * ================= */
	
	/**
	 * Renvoie la valeur représentée par c si c'est une constante
	 * @param c Le composant dont on souhaite connaître la valeur
	 * @return Sa valeur si c'est une constante, null sinon
	 */
	public static Integer evaluer(final Composant c) {
		if (c instanceof VConstante) {
			return ((VConstante) c).valeur;
		} else {
			return null;
		}
	}
}
