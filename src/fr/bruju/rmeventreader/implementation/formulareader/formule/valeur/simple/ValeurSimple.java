package fr.bruju.rmeventreader.implementation.formulareader.formule.valeur.simple;

import java.util.function.UnaryOperator;

import fr.bruju.rmeventreader.implementation.formulareader.formule.valeur.Valeur;

interface ValeurSimple extends Valeur {
	@Override
	public default int getPriorite() {
		return 0;
	}
	
	@Override
	default Valeur deleguerTraitement(UnaryOperator<Valeur> valeur) {
		return this;
	}
	
	@Override
	public default boolean estSimilaire(Valeur valeurAutre) {
		return equals(valeurAutre);
	}

	@Override
	public default Valeur similariser(Valeur valeurAutre) {
		return this;
	}
}
