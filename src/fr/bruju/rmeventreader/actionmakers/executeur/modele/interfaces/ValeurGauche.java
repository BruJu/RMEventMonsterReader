package fr.bruju.rmeventreader.actionmakers.executeur.modele.interfaces;


import fr.bruju.rmeventreader.actionmakers.executeur.modele.visiteur.VisiteurValeurGauche;

public interface ValeurGauche {
	public <T> T accept(VisiteurValeurGauche<T> visiteur) ;
}
