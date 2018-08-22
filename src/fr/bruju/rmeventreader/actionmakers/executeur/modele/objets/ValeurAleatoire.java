package fr.bruju.rmeventreader.actionmakers.executeur.modele.objets;

import fr.bruju.rmeventreader.actionmakers.executeur.modele.interfaces.ValeurDroite;
import fr.bruju.rmeventreader.actionmakers.executeur.modele.visiteur.VisiteurValeurDroite;

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
	public <T> T accept(VisiteurValeurDroite<T> visiteur) throws ObjetNonSupporte {
		return visiteur.visit(this);
	}
}
