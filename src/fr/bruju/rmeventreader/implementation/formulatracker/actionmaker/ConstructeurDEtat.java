package fr.bruju.rmeventreader.implementation.formulatracker.actionmaker;

import java.util.HashMap;
import java.util.Map;

import fr.bruju.rmeventreader.actionmakers.actionner.ActionMakerDefalse;
import fr.bruju.rmeventreader.actionmakers.actionner.Operator;
import fr.bruju.rmeventreader.actionmakers.donnees.ValeurAleatoire;
import fr.bruju.rmeventreader.actionmakers.donnees.ValeurFixe;
import fr.bruju.rmeventreader.actionmakers.donnees.Variable;

public class ConstructeurDEtat implements ActionMakerDefalse {
	
	
	// Traiteur traiteurParDefaut = new TraiteurDefaut(this);
	Map<Integer, Traiteur> variablesSpeciales = new HashMap<>();
	
	// CHANGEMENTS DE VALEUR

	// VARIABLE

	@Override
	public void changeVariable(Variable variable, Operator operator, ValeurFixe returnValue) {
		ActionMakerDefalse.super.changeVariable(variable, operator, returnValue);
	}

	@Override
	public void changeVariable(Variable variable, Operator operator, ValeurAleatoire returnValue) {
		ActionMakerDefalse.super.changeVariable(variable, operator, returnValue);
	}

	@Override
	public void changeVariable(Variable variable, Operator operator, Variable returnValue) {
		ActionMakerDefalse.super.changeVariable(variable, operator, returnValue);
	}

	// CONDITIONS
	
	
	@Override
	public boolean condOnSwitch(int number, boolean value) {
		return ActionMakerDefalse.super.condOnSwitch(number, value);
	}

	@Override
	public boolean condOnEquippedItem(int heroId, int itemId) {
		return ActionMakerDefalse.super.condOnEquippedItem(heroId, itemId);
	}

	@Override
	public boolean condOnVariable(int leftOperandValue, Operator operatorValue, ValeurFixe returnValue) {
		return ActionMakerDefalse.super.condOnVariable(leftOperandValue, operatorValue, returnValue);
	}

	@Override
	public void condElse() {
	}

	@Override
	public void condEnd() {
	}

	public Traiteur getTraiteurParDefaut() {
		return null;
		//return this.traiteurParDefaut;
	}
}
