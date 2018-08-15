package fr.bruju.rmeventreader.actionmakers.executeur.modele.objets;

import fr.bruju.rmeventreader.actionmakers.executeur.modele.interfaces.Fonction;
import fr.bruju.rmeventreader.actionmakers.executeur.modele.interfaces.ValeurDroite;

public class ValeurAleatoire implements ValeurDroite {
	public final int valeurMin;
	public final int valeurMax;
	
	public ValeurAleatoire(int valeurMin, int valeurMax) {
		this.valeurMin = valeurMin;
		this.valeurMax = valeurMax;
	}
	
	public int getMin() {
		return valeurMin;
	}
	public int getMax() {
		return valeurMax;
	}

	@Override
	public <T> T execVD(Fonction<ValeurFixe, T> fixe, Fonction<ValeurAleatoire, T> aleatoire,
			Fonction<Variable, T> variable, Fonction<Pointeur, T> pointeur) throws ObjetNonSupporte {
		return aleatoire.apply(this);
	}
}
