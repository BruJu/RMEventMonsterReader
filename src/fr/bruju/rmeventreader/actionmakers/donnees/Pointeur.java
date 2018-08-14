package fr.bruju.rmeventreader.actionmakers.donnees;


/**
 * Un pointeur est une variable ou un interrupteur dont le numéro est inscrit dans une variable
 * @author Bruju
 *
 */
public class Pointeur implements LeftValue, RightValue {
	/** Donne le numéro de la variable possédant le numéro de l'interrupteur ou de la variable voulue */
	public final int pointeur;
	
	/**
	 * Crée un pointeur vers la variable ou l'interrupteur dont le numéro est inscrit dans une variable
	 * @param pointeur Le numéro de la variable où est incrit le numéro de la varible ou de l'interrupteur
	 */
	public Pointeur(int pointeur) {
		this.pointeur = pointeur;
	}
}
