package fr.bruju.rmeventreader.actionmakers.executeur.modele.visiteur;

import fr.bruju.rmeventreader.actionmakers.executeur.modele.interfaces.ValeurDroiteVariable;
import fr.bruju.rmeventreader.actionmakers.executeur.modele.objets.NombreObjet;
import fr.bruju.rmeventreader.actionmakers.executeur.modele.objets.ObjetNonSupporte;
import fr.bruju.rmeventreader.actionmakers.executeur.modele.objets.Pointeur;
import fr.bruju.rmeventreader.actionmakers.executeur.modele.objets.ValeurAleatoire;
import fr.bruju.rmeventreader.actionmakers.executeur.modele.objets.ValeurDeplacable;
import fr.bruju.rmeventreader.actionmakers.executeur.modele.objets.ValeurDivers;
import fr.bruju.rmeventreader.actionmakers.executeur.modele.objets.ValeurFixe;
import fr.bruju.rmeventreader.actionmakers.executeur.modele.objets.Variable;
import fr.bruju.rmeventreader.actionmakers.executeur.modele.objets.VariableHeros;

public interface VisiteurValeurDroiteVariable<T> {
	public default T visit(ValeurDroiteVariable valeur) throws ObjetNonSupporte {
		return valeur.accept(this);
	}

	public default T visit(ValeurFixe valeur) throws ObjetNonSupporte {
		throw new ObjetNonSupporte();
	}
	
	public default T visit(Variable variable) throws ObjetNonSupporte {
		throw new ObjetNonSupporte();
	}
	
	public default T visit(Pointeur pointeur) throws ObjetNonSupporte {
		throw new ObjetNonSupporte();
	}

	public default T visit(ValeurAleatoire valeurAleatoire) throws ObjetNonSupporte {
		throw new ObjetNonSupporte();
	}

	public default T visit(NombreObjet objets) throws ObjetNonSupporte {
		throw new ObjetNonSupporte();
	}
	
	public default T visit(VariableHeros statHeros) throws ObjetNonSupporte {
		throw new ObjetNonSupporte();
	}
	
	public default T visit(ValeurDeplacable valeur) throws ObjetNonSupporte {
		throw new ObjetNonSupporte();
	}
	
	public default T visit(ValeurDivers valeurDivers) throws ObjetNonSupporte {
		throw new ObjetNonSupporte();
	}
}
