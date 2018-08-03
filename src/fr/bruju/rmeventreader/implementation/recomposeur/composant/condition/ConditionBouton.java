package fr.bruju.rmeventreader.implementation.recomposeur.composant.condition;

import java.util.Objects;

import fr.bruju.rmeventreader.implementation.recomposeur.composant.bouton.Bouton;
import fr.bruju.rmeventreader.implementation.recomposeur.composant.bouton.BoutonConstant;
import fr.bruju.rmeventreader.implementation.recomposeur.composant.visiteur.Visiteur;

/**
 * Condition portant sur l'état d'un interrupteur
 * 
 * @author Bruju
 *
 */
public class ConditionBouton implements Condition {
	/* =========
	 * COMPOSANT
	 * ========= */
	/** L'interrupteur dont on se questionne sur l'état */
	public final Bouton interrupteur;
	/** L'état désiré pour respecter la condition */
	public final boolean valeur;

	/**
	 * Construit une condition portant sur l'état d'un interrupteur
	 * @param interrupteur L'interrupteur
	 * @param valeur La valeur souhaitée
	 */
	public ConditionBouton(Bouton interrupteur, boolean valeur) {
		this.interrupteur = interrupteur;
		this.valeur = valeur;
	}

	@Override
	public Condition revert() {
		return new ConditionBouton(interrupteur, !valeur);
	}
	
	/* ===============
	 * IMPLEMENTATIONS
	 * =============== */

	@Override
	public String toString() {
		String s = "";
		
		if (!valeur)
			s += "¬";
		
		s += interrupteur.toString();
		
		return s;
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
		Bouton boutonS = interrupteur.simplifier();
		
		if (boutonS instanceof BoutonConstant) {
			return ConditionFixe.get(((BoutonConstant) boutonS).valeur == valeur);
		} else {
			if (boutonS == interrupteur) {
				return this;
			} else {
				return new ConditionBouton(boutonS, valeur);
			}
		}
	}

	/* =================
	 * EQUALS / HASHCODE
	 * ================= */

	@Override
	public boolean equals(final Object other) {
		if (!(other instanceof ConditionBouton)) {
			return false;
		}
		ConditionBouton castOther = (ConditionBouton) other;
		return Objects.equals(interrupteur, castOther.interrupteur) && Objects.equals(valeur, castOther.valeur);
	}

	@Override
	public int hashCode() {
		return Objects.hash("CBOUTON", interrupteur, valeur);
	}
}
