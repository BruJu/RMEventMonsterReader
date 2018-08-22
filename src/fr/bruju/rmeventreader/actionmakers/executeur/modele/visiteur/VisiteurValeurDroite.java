package fr.bruju.rmeventreader.actionmakers.executeur.modele.visiteur;

import fr.bruju.rmeventreader.actionmakers.executeur.modele.interfaces.ValeurDroite;
import fr.bruju.rmeventreader.actionmakers.executeur.modele.objets.ObjetNonSupporte;
import fr.bruju.rmeventreader.actionmakers.executeur.modele.objets.Pointeur;
import fr.bruju.rmeventreader.actionmakers.executeur.modele.objets.ValeurFixe;
import fr.bruju.rmeventreader.actionmakers.executeur.modele.objets.Variable;

public interface VisiteurValeurDroite<T> {
	public default T visit(ValeurDroite valeur) throws ObjetNonSupporte {
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
}
