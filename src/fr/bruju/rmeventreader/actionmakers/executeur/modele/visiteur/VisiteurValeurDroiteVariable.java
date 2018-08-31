package fr.bruju.rmeventreader.actionmakers.executeur.modele.visiteur;

import fr.bruju.rmeventreader.actionmakers.executeur.modele.interfaces.ValeurDroiteVariable;
import fr.bruju.rmeventreader.actionmakers.executeur.modele.objets.NombreObjet;

import fr.bruju.rmeventreader.actionmakers.executeur.modele.objets.Pointeur;
import fr.bruju.rmeventreader.actionmakers.executeur.modele.objets.ValeurAleatoire;
import fr.bruju.rmeventreader.actionmakers.executeur.modele.objets.ValeurDeplacable;
import fr.bruju.rmeventreader.actionmakers.executeur.modele.objets.ValeurDivers;
import fr.bruju.rmeventreader.actionmakers.executeur.modele.objets.ValeurFixe;
import fr.bruju.rmeventreader.actionmakers.executeur.modele.objets.Variable;
import fr.bruju.rmeventreader.actionmakers.executeur.modele.objets.VariableHeros;

public interface VisiteurValeurDroiteVariable<T> {
	public default T visit(ValeurDroiteVariable valeur)  {
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

	public default T visit(ValeurAleatoire valeurAleatoire)  {
		return null;
	}

	public default T visit(NombreObjet objets)  {
		return null;
	}
	
	public default T visit(VariableHeros statHeros)  {
		return null;
	}
	
	public default T visit(ValeurDeplacable valeur)  {
		return null;
	}
	
	public default T visit(ValeurDivers valeurDivers)  {
		return null;
	}
}
