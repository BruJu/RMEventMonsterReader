package fr.bruju.rmeventreader.actionmakers.executeur.modele.objets;

import java.util.function.Function;


public class ValeurFixe implements FixeVariable, ValeurDroite, ValeurDroiteVariable, ValeurMembre {
	public final int valeur;
	
	public ValeurFixe(int valeur) {
		this.valeur = valeur;
	}

	@Override
	public <T> T appliquerFV(Function<ValeurFixe, T> fonctionFixe, Function<Variable, T> fonctionVariable) {
		return fonctionFixe == null ? null : fonctionFixe.apply(this);
	}

	@Override
	public <T> T appliquerMembre(Function<Tous, T> fonctionTous, Function<ValeurFixe, T> fonctionFixe,
			Function<Variable, T> fonctionVariable) {
		return fonctionFixe == null ? null : fonctionFixe.apply(this);
	}

	@Override
	public <T> T appliquerDroiteVariable(Function<ValeurFixe, T> fonctionFixe, Function<Variable, T> variable,
			Function<Pointeur, T> pointeur, Function<ValeurAleatoire, T> aleatoire, Function<NombreObjet, T> objets,
			Function<VariableHeros, T> heros, Function<ValeurDeplacable, T> deplacable,
			Function<ValeurDivers, T> divers) {
		return fonctionFixe == null ? null : fonctionFixe.apply(this);
	}

	@Override
	public <T> T appliquerDroite(Function<ValeurFixe, T> fonctionFixe, Function<Variable, T> fonctionVariable,
			Function<Pointeur, T> fonctionPointeur) {
		return fonctionFixe == null ? null : fonctionFixe.apply(this);
	}
}
