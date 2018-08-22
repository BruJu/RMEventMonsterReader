package fr.bruju.rmeventreader.actionmakers.executeur.modele.interfaces;

import fr.bruju.rmeventreader.actionmakers.executeur.modele.objets.ObjetNonSupporte;
import fr.bruju.rmeventreader.actionmakers.executeur.modele.visiteur.VisiteurValeurDroite;

public interface ValeurDroite {
	public <T> T accept(VisiteurValeurDroite<T> visiteur) throws ObjetNonSupporte;
}
