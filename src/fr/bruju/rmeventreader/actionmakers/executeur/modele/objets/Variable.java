package fr.bruju.rmeventreader.actionmakers.executeur.modele.objets;

import fr.bruju.rmeventreader.actionmakers.executeur.modele.interfaces.FixeVariable;
import fr.bruju.rmeventreader.actionmakers.executeur.modele.interfaces.Fonction;
import fr.bruju.rmeventreader.actionmakers.executeur.modele.interfaces.ValeurDroite;
import fr.bruju.rmeventreader.actionmakers.executeur.modele.interfaces.ValeurGauche;

public class Variable implements FixeVariable, ValeurGauche, ValeurDroite {
	public final int idVariable;
	
	public Variable(int idVariable) {
		this.idVariable = idVariable;
	}

	@Override
	public <T> T execFV(Fonction<ValeurFixe, T> fixe, Fonction<Variable, T> variable) throws ObjetNonSupporte {
		return variable.apply(this);
	}

	@Override
	public <T> T execVG(Fonction<Variable, T> variable, Fonction<VariablePlage, T> plage,
			Fonction<Pointeur, T> pointeur) throws ObjetNonSupporte {
		return variable.apply(this);
	}

	@Override
	public <T> T execVD(Fonction<ValeurFixe, T> fixe, Fonction<ValeurAleatoire, T> aleatoire,
			Fonction<Variable, T> variable, Fonction<Pointeur, T> pointeur) throws ObjetNonSupporte {
		return variable.apply(this);
	}
}
