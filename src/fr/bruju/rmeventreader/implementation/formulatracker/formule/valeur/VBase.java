package fr.bruju.rmeventreader.implementation.formulatracker.formule.valeur;

import fr.bruju.rmeventreader.implementation.formulatracker.simplification.VisiteurDeComposants;
import java.util.Objects;

public class VBase implements Valeur {
	public final int idVariable;
	
	public VBase(Integer idVariable) {
		this.idVariable = idVariable;
		
	}

	@Override
	public String getString() {
		return "V[" + idVariable + "]";
	}


	@Override
	public void accept(VisiteurDeComposants visiteurDeComposant) {
		visiteurDeComposant.visit(this);
	}

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
