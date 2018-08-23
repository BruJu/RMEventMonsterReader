package fr.bruju.rmeventreader.actionmakers.executeur.modele.objets;

import fr.bruju.rmeventreader.actionmakers.executeur.modele.interfaces.ValeurDroiteVariable;
import fr.bruju.rmeventreader.actionmakers.executeur.modele.visiteur.VisiteurValeurDroiteVariable;

public enum ValeurDivers implements ValeurDroiteVariable {
	MONNAIE,
	SECONDES_CHRONO1,
	SECONDES_CHRONO2,
	NB_MEMBRES,
	NB_SAUVEGARDES,
	NB_COMBATS,
	NB_VICTOIRES,
	NB_DEFAITES,
	NB_FUITES,
	TEMPS_MS_MIDI;
	
	@Override
	public <T> T accept(VisiteurValeurDroiteVariable<T> visiteur) throws ObjetNonSupporte {
		return visiteur.visit(this);
	}
}
