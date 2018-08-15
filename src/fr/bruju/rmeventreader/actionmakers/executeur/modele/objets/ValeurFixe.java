package fr.bruju.rmeventreader.actionmakers.executeur.modele.objets;

import fr.bruju.rmeventreader.actionmakers.executeur.modele.interfaces.FixeVariable;
import fr.bruju.rmeventreader.actionmakers.executeur.modele.interfaces.Fonction;
import fr.bruju.rmeventreader.actionmakers.executeur.modele.interfaces.ValeurDroite;

public class ValeurFixe implements FixeVariable, ValeurDroite {
	public final int valeur;
	
	public ValeurFixe(int valeur) {
		this.valeur = valeur;
	}

	@Override
	public <T> T execFV(Fonction<ValeurFixe, T> fixe, Fonction<Variable, T> variable) throws ObjetNonSupporte {
		return fixe.apply(this);
	}

	@Override
	public <T> T execVD(Fonction<ValeurFixe, T> fixe, Fonction<ValeurAleatoire, T> aleatoire,
			Fonction<Variable, T> variable, Fonction<Pointeur, T> pointeur) throws ObjetNonSupporte {
		return fixe.apply(this);
	}
}
