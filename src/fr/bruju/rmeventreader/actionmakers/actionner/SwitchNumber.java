package fr.bruju.rmeventreader.actionmakers.actionner;

/**
 * Modification d'une variable ou d'un interrupteur
 */
public class SwitchNumber {
	// TODO : Faire 3 classes diff�rentes
	
	/**
	 * Premi�re variable modifi�e
	 */
	public final int numberDebut;
	
	/**
	 * Deuxi�me variable modifi�e
	 */
	public final int numberFin;
	
	/**
	 * Si vrai, alors c'est la variable point�e qui doit �tre modifi�e
	 */
	public final boolean pointed;
	
	/**
	 * Cr�e une variable � modifier
	 * @param number Le num�ro de la variable � modifi�e
	 * @param pointed Si vrai, il s'agit d'un pointeur, sinon du num�ro de la variable
	 */
	public SwitchNumber(int number, boolean pointed) {
		this.numberDebut = number;
		this.numberFin = number;
		this.pointed = pointed;
	}
	
	/**
	 * Cr�e une plage de variables � modifier
	 * @param debut La premi�re variable � modifier
	 * @param fin La derni�re variable � modifier
	 */
	public SwitchNumber(int debut, int fin) {
		this.numberDebut = debut;
		this.numberFin = fin;
		this.pointed = false;
	}
}
