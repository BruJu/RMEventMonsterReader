package fr.bruju.rmeventreader.actionmakers.executeur.modele.interfaces;


import fr.bruju.rmeventreader.actionmakers.executeur.modele.visiteur.VisiteurMembre;

public interface ValeurMembre {
	public <T> T accept(VisiteurMembre<T> visiteur) ;
}
