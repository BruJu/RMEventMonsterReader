package fr.bruju.rmeventreader.implementation.formulatracker.actionmaker;

import fr.bruju.rmeventreader.actionmakers.executeur.modele.Comparateur;
import fr.bruju.rmeventreader.actionmakers.executeur.modele.ValeurAleatoire;
import fr.bruju.rmeventreader.actionmakers.executeur.modele.ValeurFixe;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.bouton.BConstant;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.bouton.Bouton;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.condition.CArme;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.condition.CSwitch;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.condition.CVariable;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.condition.Condition;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.valeur.VAleatoire;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.valeur.VConstante;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.valeur.Valeur;

/**
 * Convertisseur de variables de type ActionMaker en variables de type FormulaTracker
 * 
 * @author Bruju
 *
 */
public class Traducteur {
	/** Valeur constante */
	public Valeur getValue(ValeurFixe returnValue) {
		return new VConstante(returnValue.valeur);
	}

	/** Valeur aléatoire */
	public Valeur getValue(ValeurAleatoire returnValue) {
		return new VAleatoire(returnValue.valeurMin, returnValue.valeurMax);
	}

	/** Booléen */
	public Bouton getValue(boolean value) {
		return BConstant.get(value);
	}

	/** Condition sur une variable */
	public Condition getConditionVariable(Valeur gauche, Comparateur operateur, Valeur vDroite) {
		return new CVariable(gauche, operateur, vDroite);
	}

	/** Condition sur un switch */
	public Condition getConditionSwitch(Bouton interrupteur, boolean valeur) {
		return new CSwitch(interrupteur, valeur);
	}

	/** Condition sur un objet équipé */
	public Condition getConditionObjetEquipe(int heros, int objet) {
		return new CArme(heros, objet);
	}
}