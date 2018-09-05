package fr.bruju.rmeventreader.actionmakers.executeur.modele;

import java.util.function.Function;

public class ValeurDeplacable implements ValeurDroiteVariable {
	public final EvenementDeplacable deplacable;
	public final Caracteristique caracteristique;
	
	public ValeurDeplacable(EvenementDeplacable deplacable, Caracteristique caracteristique) {
		this.deplacable = deplacable;
		this.caracteristique = caracteristique;
	}

	
	public static enum Caracteristique {
		ID_MAP,
		X,
		Y,
		DIRECTION,
		X_RELATIF_ECRAN,
		Y_RELATIF_ECRAN
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
		return deplacable == null ? null : deplacable.apply(this);
	}
}
