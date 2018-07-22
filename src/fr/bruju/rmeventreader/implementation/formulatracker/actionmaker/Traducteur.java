package fr.bruju.rmeventreader.implementation.formulatracker.actionmaker;

import fr.bruju.rmeventreader.actionmakers.actionner.Operator;
import fr.bruju.rmeventreader.actionmakers.donnees.ValeurAleatoire;
import fr.bruju.rmeventreader.actionmakers.donnees.ValeurFixe;
import fr.bruju.rmeventreader.implementation.formulatracker.formule.bouton.BConstant;
import fr.bruju.rmeventreader.implementation.formulatracker.formule.bouton.Bouton;
import fr.bruju.rmeventreader.implementation.formulatracker.formule.condition.CArme;
import fr.bruju.rmeventreader.implementation.formulatracker.formule.condition.CSwitch;
import fr.bruju.rmeventreader.implementation.formulatracker.formule.condition.CVariable;
import fr.bruju.rmeventreader.implementation.formulatracker.formule.condition.Condition;
import fr.bruju.rmeventreader.implementation.formulatracker.formule.valeur.VAleatoire;
import fr.bruju.rmeventreader.implementation.formulatracker.formule.valeur.VConstante;
import fr.bruju.rmeventreader.implementation.formulatracker.formule.valeur.Valeur;

public class Traducteur {
	public Valeur getValue(ValeurFixe returnValue) {
		return new VConstante(returnValue.get());
	}

	public Valeur getValue(ValeurAleatoire returnValue) {
		return new VAleatoire(returnValue.getMin(), returnValue.getMax());
	}
	
	public Bouton getValue(boolean value) {
		return BConstant.get(value);
	}
	
	public Condition getConditionVariable(Valeur gauche, Operator operateur, Valeur vDroite) {
		return new CVariable(gauche, operateur, vDroite);
	}
	
	public Condition getConditionSwitch(Bouton interrupteur, boolean valeur) {
		return new CSwitch(interrupteur, valeur);
	}
	
	public Condition getConditionObjetEquipe(int heros, int objet) {
		return new CArme(heros, objet);
	}
	
	
	
}
