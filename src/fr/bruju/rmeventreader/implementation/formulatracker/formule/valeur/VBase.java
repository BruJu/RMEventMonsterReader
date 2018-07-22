package fr.bruju.rmeventreader.implementation.formulatracker.formule.valeur;

import fr.bruju.rmeventreader.implementation.formulatracker.simplification.VisiteurDeComposants;

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
}
