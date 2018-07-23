package fr.bruju.rmeventreader.implementation.formulatracker.formule.condition;

import fr.bruju.rmeventreader.implementation.formulatracker.formule.bouton.Bouton;
import fr.bruju.rmeventreader.implementation.formulatracker.simplification.VisiteurDeComposants;
import java.util.Objects;

public class CSwitch implements Condition {
	public final Bouton interrupteur;
	public final boolean valeur;

	public CSwitch(Bouton interrupteur, boolean valeur) {
		this.interrupteur = interrupteur;
		this.valeur = valeur;
	}

	@Override
	public Condition revert() {
		return new CSwitch(interrupteur, !valeur);
	}

	@Override
	public String getString() {
		String s = "";
		
		if (!valeur)
			s += "¬";
		
		s += interrupteur.getString();
		
		return s;
	}

	@Override
	public void accept(VisiteurDeComposants visiteurDeComposant) {
		visiteurDeComposant.visit(this);
	}

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
