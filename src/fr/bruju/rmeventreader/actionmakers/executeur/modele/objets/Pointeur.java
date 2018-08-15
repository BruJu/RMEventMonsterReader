package fr.bruju.rmeventreader.actionmakers.executeur.modele.objets;

import fr.bruju.rmeventreader.actionmakers.executeur.modele.interfaces.Fonction;
import fr.bruju.rmeventreader.actionmakers.executeur.modele.interfaces.ValeurDroite;
import fr.bruju.rmeventreader.actionmakers.executeur.modele.interfaces.ValeurGauche;

/**
 * Un pointeur est une variable ou un interrupteur dont le numéro est inscrit dans une variable
 * @author Bruju
 *
 */
public class Pointeur implements ValeurGauche, ValeurDroite {
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
	public <T> T execVG(Fonction<Variable, T> variable, Fonction<VariablePlage, T> plage,
			Fonction<Pointeur, T> pointeur) throws ObjetNonSupporte {
		return pointeur.apply(this);
	}

	@Override
	public <T> T execVD(Fonction<ValeurFixe, T> fixe, Fonction<ValeurAleatoire, T> aleatoire,
			Fonction<Variable, T> variable, Fonction<Pointeur, T> pointeur) throws ObjetNonSupporte {
		return pointeur.apply(this);
	}
}
