package fr.bruju.rmeventreader.actionmakers.executeur.modele;

import java.util.function.Function;


public enum ValeurDivers implements ValeurDroiteVariable {
	MONNAIE,
	SECONDES_CHRONO1,
	SECONDES_CHRONO2,
	NB_MEMBRES,
	NB_SAUVEGARDES,
	NB_COMBATS,
	NB_VICTOIRES,
	NB_DEFAITES,
	NB_FUITES,
	TEMPS_MS_MIDI;

	@Override
	public <T> T appliquerDroite(Function<ValeurFixe, T> fonctionFixe, Function<Variable, T> fonctionVariable,
			Function<Pointeur, T> fonctionPointeur) {
		return null;
	}

	@Override
	public <T> T appliquerDroiteVariable(Function<ValeurFixe, T> fonctionFixe, Function<Variable, T> variable,
			Function<Pointeur, T> pointeur, Function<ValeurAleatoire, T> aleatoire, Function<NombreObjet, T> objets,
			Function<VariableHeros, T> heros, Function<ValeurDeplacable, T> deplacable,
			Function<ValeurDivers, T> divers) {
		return divers == null ? null : divers.apply(this);
	}
	
}
