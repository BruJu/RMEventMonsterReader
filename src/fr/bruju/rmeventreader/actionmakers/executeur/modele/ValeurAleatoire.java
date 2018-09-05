package fr.bruju.rmeventreader.actionmakers.executeur.modele;

import java.util.function.Function;

public class ValeurAleatoire implements ValeurDroiteVariable {
	public final int valeurMin;
	public final int valeurMax;
	
	public ValeurAleatoire(int valeurMin, int valeurMax) {
		this.valeurMin = valeurMin;
		this.valeurMax = valeurMax;
	}
	
	public int getMin() {
		return valeurMin;
	}
	
	public int getMax() {
		return valeurMax;
	}

	@Override
	public <T> T appliquerDroiteVariable(Function<ValeurFixe, T> fonctionFixe, Function<Variable, T> variable,
			Function<Pointeur, T> pointeur, Function<ValeurAleatoire, T> aleatoire, Function<NombreObjet, T> objets,
			Function<VariableHeros, T> heros, Function<ValeurDeplacable, T> deplacable,
			Function<ValeurDivers, T> divers) {
		return aleatoire == null ? null : aleatoire.apply(this);
	}

	@Override
	public <T> T appliquerDroite(Function<ValeurFixe, T> fonctionFixe, Function<Variable, T> fonctionVariable,
			Function<Pointeur, T> fonctionPointeur) {
		return null;
	}	
}
