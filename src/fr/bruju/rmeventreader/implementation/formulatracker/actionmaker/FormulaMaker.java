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
import fr.bruju.rmeventreader.implementation.formulatracker.formule.bouton.Bouton;
import fr.bruju.rmeventreader.implementation.formulatracker.formule.valeur.VStatistique;
import fr.bruju.rmeventreader.implementation.formulatracker.formule.valeur.Valeur;

public class FormulaMaker implements ActionMakerDefalse {

	private Traducteur traducteur;
	private Traiteur traiteurParDefaut;
	private Map<Integer, Traiteur> traiteursSpeciaux;
	private EtatMemoire etat;

	public FormulaMaker(Personnages contexte) {
		Map<Integer, Valeur> variablesExistantes = new HashMap<>();
		Map<Integer, Bouton> interrupteursExistants = new HashMap<>();

		traiteursSpeciaux = fixerPersonnages(contexte, variablesExistantes, interrupteursExistants);

		etat = new EtatMemoire(variablesExistantes, interrupteursExistants);

		traducteur = new Traducteur(this);
		traiteurParDefaut = new Traiteur(this);
	}
	
	private Map<Integer, Traiteur> fixerPersonnages(Personnages contexte, Map<Integer, Valeur> variablesExistantes,
			Map<Integer, Bouton> interrupteursExistants) {
		Map<Integer, Traiteur> traiteursSpeciaux = new HashMap<>();
		
		contexte.getPersonnages().stream().flatMap(p -> p.getStatistiques().values().stream()).forEach(stat -> {
			variablesExistantes.put(stat.getPosition(), new VStatistique(stat.getPossesseur(), stat.getNom()));
			traiteursSpeciaux.put(stat.getPosition(), new TraiteurEnregistreur(this, stat));
		});
		
		return traiteursSpeciaux;
	}
	
	// Entrées / Sorties



	public EtatMemoire getEtat() {
		return this.etat;
	}

	public Resultat getResultat() {
		return null;
	}

	private Traiteur getTraiteur(int numVariable) {
		return traiteursSpeciaux.getOrDefault(numVariable, traiteurParDefaut);
	}

	/* ============
	 * ACTION MAKER
	 * ============ */

	// CHANGEMENTS DE VALEUR

	// VARIABLE

	@Override
	public void changeVariable(Variable variable, Operator operator, ValeurFixe returnValue) {
		Valeur vGauche = traducteur.getValue(variable);
		Valeur vDroite = traducteur.getValue(returnValue);
		Traiteur traiteur = getTraiteur(variable.get());

		traiteur.changeVariable(vGauche, operator, vDroite);
	}

	@Override
	public void changeVariable(Variable variable, Operator operator, ValeurAleatoire returnValue) {
		Valeur vGauche = traducteur.getValue(variable);
		Valeur vDroite = traducteur.getValue(returnValue);
		Traiteur traiteur = getTraiteur(variable.get());

		traiteur.changeVariable(vGauche, operator, vDroite);
	}

	@Override
	public void changeVariable(Variable variable, Operator operator, Variable returnValue) {
		Valeur vGauche = traducteur.getValue(variable);
		Valeur vDroite = traducteur.getValue(returnValue);
		Traiteur traiteur = getTraiteur(variable.get());

		traiteur.changeVariable(vGauche, operator, vDroite);
	}

	// CONDITIONS

	@Override
	public boolean condOnSwitch(int number, boolean value) {
		Bouton interrupteur = traducteur.getInterrupteur(number);
		Bouton valeur = traducteur.getValue(value);
		
		return traiteurParDefaut.condOnSwitch(interrupteur, valeur);
	}

	@Override
	public boolean condOnEquippedItem(int heroId, int itemId) {
		return traiteurParDefaut.condOnEquippedItem(heroId, itemId);
	}

	@Override
	public boolean condOnVariable(int leftOperandValue, Operator operatorValue, ValeurFixe returnValue) {
		Valeur vGauche = traducteur.getValeurVariable(leftOperandValue);
		Valeur vDroite = traducteur.getValue(returnValue);
		
		return traiteurParDefaut.condOnVariable(vGauche, operatorValue, vDroite);
	}

	@Override
	public void condElse() {
		traiteurParDefaut.condElse();
	}

	@Override
	public void condEnd() {
		traiteurParDefaut.condEnd();
	}
}
