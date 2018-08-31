package fr.bruju.rmeventreader.actionmakers.executeur.modele.objets;

import fr.bruju.rmeventreader.actionmakers.executeur.modele.interfaces.ValeurDroite;
import fr.bruju.rmeventreader.actionmakers.executeur.modele.interfaces.ValeurDroiteVariable;
import fr.bruju.rmeventreader.actionmakers.executeur.modele.interfaces.ValeurGauche;
import fr.bruju.rmeventreader.actionmakers.executeur.modele.visiteur.VisiteurValeurDroite;
import fr.bruju.rmeventreader.actionmakers.executeur.modele.visiteur.VisiteurValeurDroiteVariable;
import fr.bruju.rmeventreader.actionmakers.executeur.modele.visiteur.VisiteurValeurGauche;

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
	public <T> T accept(VisiteurValeurGauche<T> visiteur)  {
		return visiteur.visit(this);
	}

	@Override
	public <T> T accept(VisiteurValeurDroite<T> visiteur)  {
		return visiteur.visit(this);
	}
	
	@Override
	public <T> T accept(VisiteurValeurDroiteVariable<T> visiteur)  {
		return visiteur.visit(this);
	}
}
