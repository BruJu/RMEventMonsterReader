package fr.bruju.rmeventreader.actionmakers.executeur.modele.interfaces;

import fr.bruju.rmeventreader.actionmakers.executeur.modele.objets.ObjetNonSupporte;
import fr.bruju.rmeventreader.actionmakers.executeur.modele.objets.Pointeur;
import fr.bruju.rmeventreader.actionmakers.executeur.modele.objets.Variable;
import fr.bruju.rmeventreader.actionmakers.executeur.modele.objets.VariablePlage;

public interface ValeurGauche {
	public <T> T execVG(Fonction<Variable, T> variable,Fonction<VariablePlage, T> plage,
			Fonction<Pointeur, T> pointeur) throws ObjetNonSupporte;
}
