package fr.bruju.rmeventreader.actionmakers.executeur.modele.objets;

import java.util.function.Function;


/**
 * Un pointeur est une variable ou un interrupteur dont le numéro est inscrit dans une variable
 * @author Bruju
 *
 */
public class Pointeur implements ValeurGauche, ValeurDroite, ValeurDroiteVariable {
	/** Donne le numéro de la variable possédant le numéro de l'interrupteur ou de la variable voulue */
	public final int pointeur;
	
	/**
	 * Crée un pointeur vers la variable ou l'interrupteur dont le numéro est inscrit dans une variable
	 * @param pointeur Le numéro de la variable où est incrit le numéro de la varible ou de l'interrupteur
	 */
	public Pointeur(int pointeur) {
		this.pointeur = pointeur;
	}

	@Override
	public <T> T appliquerDroiteVariable(Function<ValeurFixe, T> fonctionFixe, Function<Variable, T> variable,
			Function<Pointeur, T> pointeur, Function<ValeurAleatoire, T> aleatoire, Function<NombreObjet, T> objets,
			Function<VariableHeros, T> heros, Function<ValeurDeplacable, T> deplacable,
			Function<ValeurDivers, T> divers) {
		return pointeur == null ? null : pointeur.apply(this);
	}

	@Override
	public <T> T appliquerDroite(Function<ValeurFixe, T> fonctionFixe, Function<Variable, T> fonctionVariable,
			Function<Pointeur, T> fonctionPointeur) {
		return fonctionPointeur == null ? null : fonctionPointeur.apply(this);
	}

	@Override
	public <T> T appliquerG(Function<Variable, T> variable, Function<VariablePlage, T> plage,
			Function<Pointeur, T> pointeur) {
		return pointeur == null ? null : pointeur.apply(this);
	}

	
}
