package fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.transformation;

import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.organisation.AlgorithmeEtiquete;

public interface Unificateur extends Transformateur {
	public AlgorithmeEtiquete unifier(AlgorithmeEtiquete algo1, AlgorithmeEtiquete algo2);

	@Override
	default void accept(Visiteur visiteur) {
		visiteur.visit(this);
	}
}
