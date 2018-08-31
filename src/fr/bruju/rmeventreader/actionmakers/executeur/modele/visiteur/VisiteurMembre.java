package fr.bruju.rmeventreader.actionmakers.executeur.modele.visiteur;

import fr.bruju.rmeventreader.actionmakers.executeur.modele.interfaces.ValeurMembre;

import fr.bruju.rmeventreader.actionmakers.executeur.modele.objets.Tous;
import fr.bruju.rmeventreader.actionmakers.executeur.modele.objets.ValeurFixe;
import fr.bruju.rmeventreader.actionmakers.executeur.modele.objets.Variable;

public interface VisiteurMembre<T> {
	public default T visit(ValeurMembre valeur)  {
		return valeur.accept(this);
	}

	public default T visit(ValeurFixe valeur)  {
		return null;
	}
	
	public default T visit(Variable variable)  {
		return null;
	}
	
	public default T visit(Tous tous)  {
		return null;
	}
}
