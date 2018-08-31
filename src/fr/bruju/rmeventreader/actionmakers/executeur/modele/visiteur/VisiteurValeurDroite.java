package fr.bruju.rmeventreader.actionmakers.executeur.modele.visiteur;

import fr.bruju.rmeventreader.actionmakers.executeur.modele.interfaces.ValeurDroite;

import fr.bruju.rmeventreader.actionmakers.executeur.modele.objets.Pointeur;
import fr.bruju.rmeventreader.actionmakers.executeur.modele.objets.ValeurFixe;
import fr.bruju.rmeventreader.actionmakers.executeur.modele.objets.Variable;

public interface VisiteurValeurDroite<T> {
	public default T visit(ValeurDroite valeur)  {
		return valeur.accept(this);
	}

	public default T visit(ValeurFixe valeur)  {
		return null;
	}
	
	public default T visit(Variable variable)  {
		return null;
	}
	
	public default T visit(Pointeur pointeur)  {
		return null;
	}
}
