package fr.bruju.rmeventreader.actionmakers.executeur.modele.objets;

import java.util.function.Function;

public class NombreObjet implements ValeurDroiteVariable {
	/** Numéro de l'objet */
	public final int numeroObjet;
	/** Si vrai il s'agit du nombre d'objets possédés. Si faux du nombre d'objets équipés */
	public final boolean equipe;
	
	public NombreObjet(int numeroObjet, boolean equipe) {
		this.numeroObjet = numeroObjet;
		this.equipe = equipe;
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
		return objets == null ? null : objets.apply(this);
	}

	
}
