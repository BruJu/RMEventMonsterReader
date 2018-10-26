package fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes;

import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.organisation.AlgorithmeEtiquete;

import java.util.List;
import java.util.function.Consumer;

public interface Separateur extends Transformateur {
	public void separer(Consumer<AlgorithmeEtiquete> fonctionDAjout, AlgorithmeEtiquete elementASeparer);

	@Override
	default void accept(Transformateur.Visiteur visiteur) {
		visiteur.visit(this);
	}
}
