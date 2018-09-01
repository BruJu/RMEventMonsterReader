package fr.bruju.rmeventreader.actionmakers.executeur.modele.objets;

import java.util.function.Function;

public interface ValeurDroite {
	public <T> T appliquerDroite(
			Function<ValeurFixe, T> fonctionFixe,
			Function<Variable, T> fonctionVariable,
			Function<Pointeur, T> fonctionPointeur);
}