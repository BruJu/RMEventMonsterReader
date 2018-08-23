package fr.bruju.rmeventreader.actionmakers.executeur.modele.objets;

import fr.bruju.rmeventreader.actionmakers.executeur.modele.interfaces.ValeurDroite;
import fr.bruju.rmeventreader.actionmakers.executeur.modele.interfaces.ValeurDroiteVariable;
import fr.bruju.rmeventreader.actionmakers.executeur.modele.visiteur.VisiteurValeurDroite;
import fr.bruju.rmeventreader.actionmakers.executeur.modele.visiteur.VisiteurValeurDroiteVariable;

public class ValeurAleatoire implements ValeurDroite, ValeurDroiteVariable {
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
	

	@Override
	public <T> T accept(VisiteurValeurDroiteVariable<T> visiteur) throws ObjetNonSupporte {
		return visiteur.visit(this);
	}
}
