package fr.bruju.rmeventreader.actionmakers.actionner;

/**
 * Modification d'une variable ou d'un interrupteur
 */
public class SwitchNumber {
	// TODO : Faire 3 classes différentes
	
	/**
	 * Première variable modifiée
	 */
	public final int numberDebut;
	
	/**
	 * Deuxième variable modifiée
	 */
	public final int numberFin;
	
	/**
	 * Si vrai, alors c'est la variable pointée qui doit être modifiée
	 */
	public final boolean pointed;
	
	/**
	 * Crée une variable à modifier
	 * @param number Le numéro de la variable à modifiée
	 * @param pointed Si vrai, il s'agit d'un pointeur, sinon du numéro de la variable
	 */
	public SwitchNumber(int number, boolean pointed) {
		this.numberDebut = number;
		this.numberFin = number;
		this.pointed = pointed;
	}
	
	/**
	 * Crée une plage de variables à modifier
	 * @param debut La première variable à modifier
	 * @param fin La dernière variable à modifier
	 */
	public SwitchNumber(int debut, int fin) {
		this.numberDebut = debut;
		this.numberFin = fin;
		this.pointed = false;
	}
}
