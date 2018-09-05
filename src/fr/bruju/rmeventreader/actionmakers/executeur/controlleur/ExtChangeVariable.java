package fr.bruju.rmeventreader.actionmakers.executeur.controlleur;

import fr.bruju.rmeventreader.actionmakers.executeur.modele.Pointeur;
import fr.bruju.rmeventreader.actionmakers.executeur.modele.ValeurGauche;
import fr.bruju.rmeventreader.actionmakers.executeur.modele.Variable;
import fr.bruju.rmeventreader.actionmakers.executeur.modele.VariablePlage;

public interface ExtChangeVariable {
	public default void $(ValeurGauche valeurGauche, Boolean nouvelleValeur) {
		if (nouvelleValeur == null) {
			if (valeurGauche instanceof Variable) {
				inverseSwitch((Variable) valeurGauche);
			} else if (valeurGauche instanceof VariablePlage) {
				((VariablePlage) valeurGauche).getList().forEach(this::inverseSwitch);
			} else {
				inverseSwitch((Pointeur) valeurGauche);
			}
		} else {
			if (valeurGauche instanceof Variable) {
				changeSwitch((Variable) valeurGauche, nouvelleValeur);
			} else if (valeurGauche instanceof VariablePlage) {
				((VariablePlage) valeurGauche).getList().forEach(variable -> changeSwitch(variable, nouvelleValeur));
			} else {
				changeSwitch((Pointeur) valeurGauche, nouvelleValeur);
			}
		}
	}
	
	public default void inverseSwitch(Variable interrupteur) {
	}
	
	public default void changeSwitch(Variable interrupteur, boolean nouvelleValeur) {
	}

	public default void inverseSwitch(Pointeur interrupteur) {
	}
	
	public default void changeSwitch(Pointeur interrupteur, boolean nouvelleValeur) {
	}
	
	/*
	public default void Variables_changerSwitch(ValeurGauche valeurGauche, Boolean nouvelleValeur) {
	}

	public default void Variables_affecterVariable(ValeurGauche valeurGauche, ValeurDroiteVariable valeurDroite) {
	}

	public default void Variables_changerVariable(ValeurGauche valeurGauche, OpMathematique operateur,
			ValeurDroiteVariable valeurDroite) {
	}
	*/
	
	
}
