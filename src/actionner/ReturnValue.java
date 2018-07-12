package actionner;

/**
 * Valeur de retour pour la plupart des op�rations
 */
public class ReturnValue {
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
