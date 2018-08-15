package fr.bruju.rmeventreader.actionmakers.executeur.modele.interfaces;

import fr.bruju.rmeventreader.actionmakers.executeur.modele.objets.ObjetNonSupporte;
import fr.bruju.rmeventreader.actionmakers.executeur.modele.objets.ValeurFixe;
import fr.bruju.rmeventreader.actionmakers.executeur.modele.objets.Variable;

public interface FixeVariable {
	public <T> T execFV(Fonction<ValeurFixe, T> fixe, Fonction<Variable, T> variable) throws ObjetNonSupporte;
}
