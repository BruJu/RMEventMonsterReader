package fr.bruju.rmeventreader.actionmakers.executeur.modele.objets;

import fr.bruju.rmeventreader.actionmakers.executeur.modele.interfaces.FixeVariable;
import fr.bruju.rmeventreader.actionmakers.executeur.modele.interfaces.ValeurDroite;
import fr.bruju.rmeventreader.actionmakers.executeur.modele.visiteur.VisiteurFixeVariable;
import fr.bruju.rmeventreader.actionmakers.executeur.modele.visiteur.VisiteurValeurDroite;

public class ValeurFixe implements FixeVariable, ValeurDroite {
	public final int valeur;
	
	public ValeurFixe(int valeur) {
		this.valeur = valeur;
	}

	@Override
	public <T> T accept(VisiteurValeurDroite<T> visiteur) throws ObjetNonSupporte {
		return visiteur.visit(this);
	}

	@Override
	public <T> T accept(VisiteurFixeVariable<T> visiteur) throws ObjetNonSupporte {
		return visiteur.visit(this);
	}

}
