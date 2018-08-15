package fr.bruju.rmeventreader.actionmakers.executeur.modele.interfaces;

import fr.bruju.rmeventreader.actionmakers.executeur.modele.objets.ObjetNonSupporte;
import fr.bruju.rmeventreader.actionmakers.executeur.modele.objets.Pointeur;
import fr.bruju.rmeventreader.actionmakers.executeur.modele.objets.ValeurAleatoire;
import fr.bruju.rmeventreader.actionmakers.executeur.modele.objets.ValeurFixe;
import fr.bruju.rmeventreader.actionmakers.executeur.modele.objets.Variable;

public interface ValeurDroite {
	public <T> T execVD(
			Fonction<ValeurFixe, T> fixe,
			Fonction<ValeurAleatoire, T> aleatoire,
			Fonction<Variable, T> variable,
			Fonction<Pointeur, T> pointeur) throws ObjetNonSupporte;
}
