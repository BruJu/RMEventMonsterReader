package fr.bruju.rmeventreader.actionmakers.executeur.modele;

import java.util.function.Function;

public class VariableHeros implements ValeurDroiteVariable {
	public final int idHeros;
	public final Caracteristique caracteristique;
	
	public VariableHeros(int idHeros, Caracteristique caracteristique) {
		this.idHeros = idHeros;
		this.caracteristique = caracteristique;
	}


	public static enum Caracteristique {
		NIVEAU,
		EXPERIENCE,
		HPACTUEL,
		MPACTUEL,
		HPMAX,
		MPMAX,
		ATTAQUE,
		DEFENSE,
		INTELLIGENCE,
		AGILITE,
		ARME,
		BOUCLIER,
		ARMURE,
		CASQUE,
		ACCESSOIRE
	}


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
		return heros == null ? null : heros.apply(this);
	}
}
