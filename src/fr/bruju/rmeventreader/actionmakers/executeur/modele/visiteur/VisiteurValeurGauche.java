package fr.bruju.rmeventreader.actionmakers.executeur.modele.visiteur;

import fr.bruju.rmeventreader.actionmakers.executeur.modele.interfaces.ValeurGauche;

import fr.bruju.rmeventreader.actionmakers.executeur.modele.objets.Pointeur;
import fr.bruju.rmeventreader.actionmakers.executeur.modele.objets.Variable;
import fr.bruju.rmeventreader.actionmakers.executeur.modele.objets.VariablePlage;

public interface VisiteurValeurGauche<T> {
	public default T visit(ValeurGauche valeur)  {
		return valeur.accept(this);
	}
	
	public default T visit(Variable variable)  {
		return null;
	}
	
	public default T visit(VariablePlage plage)  {
		return null;
	}
	
	public default T visit(Pointeur pointeur)  {
		return null;
	}
}
