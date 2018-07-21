package fr.bruju.rmeventreader.implementation.formulatracker.actionmaker;

import java.util.HashMap;
import java.util.Map;

import fr.bruju.rmeventreader.actionmakers.actionner.ActionMakerDefalse;
import fr.bruju.rmeventreader.actionmakers.actionner.Operator;
import fr.bruju.rmeventreader.actionmakers.donnees.ValeurAleatoire;
import fr.bruju.rmeventreader.actionmakers.donnees.ValeurFixe;
import fr.bruju.rmeventreader.actionmakers.donnees.Variable;
import fr.bruju.rmeventreader.implementation.formulatracker.contexte.Personnages;
import fr.bruju.rmeventreader.implementation.formulatracker.contexte.attaques.Resultat;
import fr.bruju.rmeventreader.implementation.formulatracker.contexte.personnage.Statistique;
import fr.bruju.rmeventreader.implementation.formulatracker.formule.valeur.VStatistique;
import fr.bruju.rmeventreader.implementation.formulatracker.formule.valeur.Valeur;

public class FormulaMaker implements ActionMakerDefalse {

	private Traducteur traducteur;
	private Map<Integer, Valeur> variablesExistantes;

	public FormulaMaker() {
		variablesExistantes = new HashMap<Integer, Valeur>();
		traducteur = new Traducteur(variablesExistantes);
	}

	// Entrées / Sorties

	public void fixerPersonnages(Personnages contexte) {
		contexte.getPersonnages().stream()
				.flatMap(p -> p.getStatistiques().values().stream())
				.forEach(stat -> {
				variablesExistantes.put(stat.getPosition(), 
							new VStatistique(stat.getPossesseur(), stat.getNom()));
				});
	}

	public Resultat getResultat() {
		return null;
	}

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
		Valeur rightValue = Traducteur.getInstance().getValue(returnValue);

		ActionMakerDefalse.super.changeVariable(variable, operator, returnValue);
	}

	@Override
	public void changeVariable(Variable variable, Operator operator, Variable returnValue) {
		Valeur rightValue = Traducteur.getInstance().getValue(returnValue);

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
		Valeur rightValue = Traducteur.getInstance().getValue(returnValue);

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
