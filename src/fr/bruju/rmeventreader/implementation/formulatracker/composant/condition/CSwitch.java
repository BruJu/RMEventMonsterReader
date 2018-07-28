package fr.bruju.rmeventreader.implementation.formulatracker.composant.condition;

import fr.bruju.rmeventreader.implementation.formulatracker.composant.Composant;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.bouton.BConstant;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.bouton.Bouton;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.visiteur.VisiteurDeComposants;

import java.util.Objects;

/**
 * Condition portant sur l'état d'un interrupteur
 * 
 * @author Bruju
 *
 */
public class CSwitch implements Condition {
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
	public CSwitch(Bouton interrupteur, boolean valeur) {
		this.interrupteur = interrupteur;
		this.valeur = valeur;
	}

	@Override
	public Condition revert() {
		return new CSwitch(interrupteur, !valeur);
	}

	/* ===============
	 * IMPLEMENTATIONS
	 * =============== */

	@Override
	public String getString() {
		String s = "";
		
		if (!valeur)
			s += "¬";
		
		s += interrupteur.getString();
		
		return s;
	}

	@Override
	public Composant evaluationRapide() {
		if (interrupteur instanceof BConstant) {
			BConstant cst = (BConstant) interrupteur;
			
			return CFixe.get(cst.value == valeur);
		}
		
		return this;
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
		if (!(other instanceof CSwitch)) {
			return false;
		}
		CSwitch castOther = (CSwitch) other;
		return Objects.equals(interrupteur, castOther.interrupteur) && Objects.equals(valeur, castOther.valeur);
	}

	@Override
	public int hashCode() {
		return Objects.hash(interrupteur, valeur) * 2953;
	}


	
}
