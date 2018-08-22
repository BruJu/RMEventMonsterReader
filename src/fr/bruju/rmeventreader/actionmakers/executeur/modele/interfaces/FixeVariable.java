package fr.bruju.rmeventreader.actionmakers.executeur.modele.interfaces;

import fr.bruju.rmeventreader.actionmakers.executeur.modele.objets.ObjetNonSupporte;
import fr.bruju.rmeventreader.actionmakers.executeur.modele.visiteur.VisiteurFixeVariable;

public interface FixeVariable {
	public <T> T accept(VisiteurFixeVariable<T> visiteur) throws ObjetNonSupporte;
}
