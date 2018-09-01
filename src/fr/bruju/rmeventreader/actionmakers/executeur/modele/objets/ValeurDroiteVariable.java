package fr.bruju.rmeventreader.actionmakers.executeur.modele.objets;

import java.util.function.Function;

public interface ValeurDroiteVariable extends ValeurDroite {
	public <T> T appliquerDroiteVariable(
			Function<ValeurFixe, T> fonctionFixe,
			Function<Variable, T> variable,
			Function<Pointeur, T> pointeur,
			Function<ValeurAleatoire, T> aleatoire,
			Function<NombreObjet, T> objets,
			Function<VariableHeros, T> heros,
			Function<ValeurDeplacable, T> deplacable,
			Function<ValeurDivers, T> divers);
}
