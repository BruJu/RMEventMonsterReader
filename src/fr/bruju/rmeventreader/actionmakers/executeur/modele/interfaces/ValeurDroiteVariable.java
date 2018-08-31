package fr.bruju.rmeventreader.actionmakers.executeur.modele.interfaces;


import fr.bruju.rmeventreader.actionmakers.executeur.modele.visiteur.VisiteurValeurDroiteVariable;

public interface ValeurDroiteVariable {
	public <T> T accept(VisiteurValeurDroiteVariable<T> visiteur) ;
}
