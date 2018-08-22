package fr.bruju.rmeventreader.actionmakers.executeur.modele.objets;

import fr.bruju.rmeventreader.actionmakers.executeur.modele.interfaces.FixeVariable;
import fr.bruju.rmeventreader.actionmakers.executeur.modele.interfaces.ValeurDroite;
import fr.bruju.rmeventreader.actionmakers.executeur.modele.interfaces.ValeurGauche;
import fr.bruju.rmeventreader.actionmakers.executeur.modele.visiteur.VisiteurFixeVariable;
import fr.bruju.rmeventreader.actionmakers.executeur.modele.visiteur.VisiteurValeurDroite;
import fr.bruju.rmeventreader.actionmakers.executeur.modele.visiteur.VisiteurValeurGauche;

public class Variable implements FixeVariable, ValeurGauche, ValeurDroite {
	public final int idVariable;
	
	public Variable(int idVariable) {
		this.idVariable = idVariable;
	}

	@Override
	public <T> T accept(VisiteurValeurDroite<T> visiteur) throws ObjetNonSupporte {
		return visiteur.visit(this);
	}

	@Override
	public <T> T accept(VisiteurValeurGauche<T> visiteur) throws ObjetNonSupporte {
		return visiteur.visit(this);
	}

	@Override
	public <T> T accept(VisiteurFixeVariable<T> visiteur) throws ObjetNonSupporte {
		return visiteur.visit(this);
	}
}
