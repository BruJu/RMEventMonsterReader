package fr.bruju.rmeventreader.actionmakers.actionner;

/**
 * Valeur de retour pour la plupart des op�rations
 */
public class ReturnValue {
	// TODO : retirer l'�num au profit de 4 impl�mentations de l'interface (qui n'existera pas) ReturnValue
	/*
	 * Le choix de condenser les informations dans une classe simple d'acc�s est un choix de design que j'ai fait
	 * dans le but de minimiser le nombre d'instructions � g�rer dans ActionMaker.
	 * N�anmoins � l'utilisation, il apparait que
	 * 1/ On impl�mente pas tout
	 * 2/ On utilise des interfaces / classes d�riv�es qui pr�impl�mentent des parties qui ne nous interessent pas
	 * 
	 * Ainsi, ce choix de design est plus propice � des bugs (oubli de v�rifier le type) que n'apporte de solutions
	 */
	
	/**
	 * Enum�ration des types possibles de valeur de retour
	 */
	public enum Type {
		/**
		 * Valeur en dur
		 */
		VALUE,
		/**
		 * Num�ro de variable
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
	 * Le num�ro de la valeur.
	 * Si il s'agit d'une plage al�atoire (possible avec Value), value est la borne minimale
	 */
	public final int value;
	
	/**
	 * La borne maximale
	 */
	public final int borneMax;
	
	/**
	 * Cr�e une valeur de retour avec un type et une valeur unique
	 * @param type Le type
	 * @param value La valeur
	 */
	public ReturnValue(Type type, int value) {
		this.type = type;
		this.value = value;
		
		this.borneMax = value;
	}
	
	/**
	 * Cr�e une valeur al�atoire de retour dans une borne
	 * @param valueMin La borne minimale
	 * @param valueMax La borne maximale
	 */
	public ReturnValue(int valueMin, int valueMax) {
		this.type = Type.VALUE;
		
		this.value = valueMin;
		this.borneMax = valueMax;
	}
	
}
