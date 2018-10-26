package fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.transformation;

import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.organisation.AlgorithmeEtiquete;
import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.transformation.Transformateur;

import java.util.List;

public interface ManipulateurDeListe extends Transformateur {

	@Override
	default void accept(Visiteur visiteur) {
		visiteur.visit(this);
	}

	public List<AlgorithmeEtiquete> manipuler(List<AlgorithmeEtiquete> liste);
}
