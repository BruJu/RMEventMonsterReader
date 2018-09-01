package fr.bruju.rmeventreader.actionmakers.executeur.modele.objets;

import java.util.function.Function;

public interface ValeurMembre {
	public <T> T appliquerMembre(Function<Tous, T> fonctionTous,
			Function<ValeurFixe, T> fonctionFixe,
			Function<Variable, T> fonctionVariable);
}
