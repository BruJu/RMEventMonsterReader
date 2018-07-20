package fr.bruju.rmeventreader.implementation.formulareader.formule.valeur.simple;

import java.util.function.UnaryOperator;

import fr.bruju.rmeventreader.implementation.formulareader.formule.valeur.Valeur;
import fr.bruju.rmeventreader.rmdatabase.Affectation;

interface ValeurSimple extends Valeur {
	@Override
	public default int getPriorite() {
		return 0;
	}
	

	@Override
	public default Valeur evaluationPartielle(Affectation affectation) {
		return this;
	}
	

	@Override
	default Valeur deleguerTraitement(UnaryOperator<Valeur> valeur) {
		return this;
	}
	
	
}
