package fr.bruju.rmeventreader.implementation.formulatracker.actionmaker;

import fr.bruju.rmeventreader.actionmakers.actionner.Operator;
import fr.bruju.rmeventreader.implementation.formulatracker.formule.bouton.Bouton;
import fr.bruju.rmeventreader.implementation.formulatracker.formule.valeur.Valeur;

public class Traiteur {

	public Traiteur(FormulaMaker formulaMaker) {
	}

	public void changeVariable(Valeur vGauche, Operator operator, Valeur vDroite) {
	}

	public boolean condOnSwitch(Bouton interrupteur, Bouton valeur) {
		
		return false;
	}

	public boolean condOnEquippedItem(int heroId, int itemId) {
		return false;
	}

	public boolean condOnVariable(Valeur vGauche, Operator operatorValue, Valeur vDroite) {
		return false;
	}

	public void condElse() {
	}


	public void condEnd() {
	}

	
}
