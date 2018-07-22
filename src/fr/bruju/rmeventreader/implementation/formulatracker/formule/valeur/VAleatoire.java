package fr.bruju.rmeventreader.implementation.formulatracker.formule.valeur;

import fr.bruju.rmeventreader.implementation.formulatracker.simplification.VisiteurDeComposants;

public class VAleatoire implements Valeur {
	public final int min;
	public final int max;
	
	
	public VAleatoire(int min, int max) {
		this.min = min;
		this.max = max;
	}


	@Override
	public String getString() {
		return min + "~" + max;
	}

	@Override
	public void accept(VisiteurDeComposants visiteurDeComposant) {
		visiteurDeComposant.visit(this);
	}
}
