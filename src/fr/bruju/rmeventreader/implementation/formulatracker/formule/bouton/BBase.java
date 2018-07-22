package fr.bruju.rmeventreader.implementation.formulatracker.formule.bouton;

import fr.bruju.rmeventreader.implementation.formulatracker.simplification.VisiteurDeComposants;

public class BBase implements Bouton {
	public final int numero; 
	
	public BBase(int numero) {
		this.numero = numero;
	}
	
	@Override
	public String getString() {
		return "S["+numero+"]";
	}

	@Override
	public void accept(VisiteurDeComposants visiteurDeComposant) {
		visiteurDeComposant.visit(this);
	}
}
