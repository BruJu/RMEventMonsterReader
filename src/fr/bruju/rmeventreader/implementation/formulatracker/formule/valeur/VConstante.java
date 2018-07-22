package fr.bruju.rmeventreader.implementation.formulatracker.formule.valeur;

import fr.bruju.rmeventreader.implementation.formulatracker.simplification.VisiteurDeComposants;

public class VConstante implements Valeur {
	public final int valeur;
	
	public VConstante(int valeur) {
		this.valeur = valeur;
	}

	@Override
	public String getString() {
		return Integer.toString(valeur);
	}


	@Override
	public void accept(VisiteurDeComposants visiteurDeComposant) {
		visiteurDeComposant.visit(this);
	}
}
