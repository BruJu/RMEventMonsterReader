package fr.bruju.rmeventreader.actionmakers.executeur.modele.visiteur;

import fr.bruju.rmeventreader.actionmakers.executeur.modele.interfaces.FixeVariable;

import fr.bruju.rmeventreader.actionmakers.executeur.modele.objets.ValeurFixe;
import fr.bruju.rmeventreader.actionmakers.executeur.modele.objets.Variable;

public interface VisiteurFixeVariable<T> {
	public default T visit(FixeVariable valeur)  {
		return valeur.accept(this);
	}

	public default T visit(ValeurFixe valeur)  {
		return null;
	}
	
	public default T visit(Variable variable)  {
		return null;
	}
}
