package fr.bruju.rmeventreader.actionmakers.modele;

import java.util.function.Function;

public interface ValeurMembre {
	public <T> T appliquerMembre(Function<Tous, T> fonctionTous,
			Function<ValeurFixe, T> fonctionFixe,
			Function<Variable, T> fonctionVariable);
}
