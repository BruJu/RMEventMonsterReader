package fr.bruju.rmeventreader.actionmakers.modele;

import java.util.function.Function;

public interface ValeurDroite {
	public <T> T appliquerDroite(
			Function<ValeurFixe, T> fonctionFixe,
			Function<Variable, T> fonctionVariable,
			Function<Pointeur, T> fonctionPointeur);
}