package fr.bruju.rmeventreader.actionmakers.actionner;

/**
 * Valeur de retour pour la plupart des opérations
 */
public class ReturnValue {
	// TODO : retirer l'énum au profit de 4 implémentations de l'interface (qui n'existera pas) ReturnValue
	/*
	 * Le choix de condenser les informations dans une classe simple d'accés est un choix de design que j'ai fait
	 * dans le but de minimiser le nombre d'instructions à gérer dans ActionMaker.
	 * Néanmoins à l'utilisation, il apparait que
	 * 1/ On implémente pas tout
	 * 2/ On utilise des interfaces / classes dérivées qui préimplémentent des parties qui ne nous interessent pas
	 * 
	 * Ainsi, ce choix de design est plus propice à des bugs (oubli de vérifier le type) que n'apporte de solutions
	 */
	
	/**
	 * Enumération des types possibles de valeur de retour
	 */
	public enum Type {
		/**
		 * Valeur en dur
		 */
		VALUE,
		/**
		 * Numéro de variable
		 */
		VARIABLE,
		/**
		 * Pointeur vers une autre variable
		 */
		POINTER
	}
	
	/**
	 * Le type de retour
	 */
	public final Type type;
	
	/**
	 * Le numéro de la valeur.
	 * Si il s'agit d'une plage aléatoire (possible avec Value), value est la borne minimale
	 */
	public final int value;
	
	/**
	 * La borne maximale
	 */
	public final int borneMax;
	
	/**
	 * Crée une valeur de retour avec un type et une valeur unique
	 * @param type Le type
	 * @param value La valeur
	 */
	public ReturnValue(Type type, int value) {
		this.type = type;
		this.value = value;
		
		this.borneMax = value;
	}
	
	/**
	 * Crée une valeur aléatoire de retour dans une borne
	 * @param valueMin La borne minimale
	 * @param valueMax La borne maximale
	 */
	public ReturnValue(int valueMin, int valueMax) {
		this.type = Type.VALUE;
		
		this.value = valueMin;
		this.borneMax = valueMax;
	}
	
}
