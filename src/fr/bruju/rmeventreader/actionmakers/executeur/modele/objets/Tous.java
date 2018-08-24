package fr.bruju.rmeventreader.actionmakers.executeur.modele.objets;

import fr.bruju.rmeventreader.actionmakers.executeur.modele.interfaces.ValeurMembre;
import fr.bruju.rmeventreader.actionmakers.executeur.modele.visiteur.VisiteurMembre;

public class Tous implements ValeurMembre {
	private static Tous instance;
	
	private Tous() {
	}
	
	public static Tous getInstance() {
		if (null == instance) {
			instance = new Tous();
		}
		return instance;
	}
	
	@Override
	public <T> T accept(VisiteurMembre<T> visiteur) throws ObjetNonSupporte {
		return visiteur.visit(this);
	}
}
