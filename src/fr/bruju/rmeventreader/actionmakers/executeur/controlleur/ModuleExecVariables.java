package fr.bruju.rmeventreader.actionmakers.executeur.controlleur;

import fr.bruju.rmeventreader.actionmakers.executeur.modele.objets.FixeVariable;
import fr.bruju.rmeventreader.actionmakers.executeur.modele.objets.OpMathematique;
import fr.bruju.rmeventreader.actionmakers.executeur.modele.objets.ValeurDroiteVariable;
import fr.bruju.rmeventreader.actionmakers.executeur.modele.objets.ValeurGauche;

public interface ModuleExecVariables {
	public static final ModuleExecVariables Null = new ModuleExecVariables() {};

	
	public default void Variables_changerSwitch(ValeurGauche valeurGauche, Boolean nouvelleValeur) {
	}

	public default void Variables_affecterVariable(ValeurGauche valeurGauche, ValeurDroiteVariable valeurDroite) {
	}

	public default void Variables_changerVariable(ValeurGauche valeurGauche, OpMathematique operateur,
			ValeurDroiteVariable valeurDroite) {
	}
	
	public default void Variables_modifierArgent(boolean ajouter, FixeVariable quantite) {
	}

	public default void Variables_modifierObjets(boolean ajouter, FixeVariable objet, FixeVariable quantite) {
	}

}
