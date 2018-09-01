package fr.bruju.rmeventreader.actionmakers.executeur.modele.objets;

import java.util.function.Function;


public class Variable implements FixeVariable, ValeurGauche, ValeurDroite, ValeurDroiteVariable, ValeurMembre {
	public final int idVariable;
	
	public Variable(int idVariable) {
		this.idVariable = idVariable;
	}

	@Override
	public <T> T appliquerFV(Function<ValeurFixe, T> fonctionFixe, Function<Variable, T> fonctionVariable) {
		return fonctionVariable == null ? null : fonctionVariable.apply(this);
	}

	@Override
	public <T> T appliquerMembre(Function<Tous, T> fonctionTous, Function<ValeurFixe, T> fonctionFixe,
			Function<Variable, T> fonctionVariable) {
		return fonctionVariable == null ? null : fonctionVariable.apply(this);
	}

	@Override
	public <T> T appliquerDroiteVariable(Function<ValeurFixe, T> fonctionFixe, Function<Variable, T> variable,
			Function<Pointeur, T> pointeur, Function<ValeurAleatoire, T> aleatoire, Function<NombreObjet, T> objets,
			Function<VariableHeros, T> heros, Function<ValeurDeplacable, T> deplacable,
			Function<ValeurDivers, T> divers) {
		return variable == null ? null : variable.apply(this);
	}

	@Override
	public <T> T appliquerDroite(Function<ValeurFixe, T> fonctionFixe, Function<Variable, T> fonctionVariable,
			Function<Pointeur, T> fonctionPointeur) {
		return fonctionVariable == null ? null : fonctionVariable.apply(this);
	}

	@Override
	public <T> T appliquerG(Function<Variable, T> variable, Function<VariablePlage, T> plage,
			Function<Pointeur, T> pointeur) {
		return variable == null ? null : variable.apply(this);
	}
}
