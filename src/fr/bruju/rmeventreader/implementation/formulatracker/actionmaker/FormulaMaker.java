package fr.bruju.rmeventreader.implementation.formulatracker.actionmaker;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fr.bruju.rmeventreader.actionmakers.actionner.ActionMakerDefalse;
import fr.bruju.rmeventreader.actionmakers.actionner.Operator;
import fr.bruju.rmeventreader.actionmakers.donnees.ValeurAleatoire;
import fr.bruju.rmeventreader.actionmakers.donnees.ValeurFixe;
import fr.bruju.rmeventreader.actionmakers.donnees.Variable;
import fr.bruju.rmeventreader.implementation.formulatracker.contexte.Personnages;
import fr.bruju.rmeventreader.implementation.formulatracker.contexte.personnage.Statistique;
import fr.bruju.rmeventreader.implementation.formulatracker.formule.bouton.Bouton;
import fr.bruju.rmeventreader.implementation.formulatracker.formule.condition.Condition;
import fr.bruju.rmeventreader.implementation.formulatracker.formule.formule.ConditionVersValeur;
import fr.bruju.rmeventreader.implementation.formulatracker.formule.formule.FormuleDeDegats;
import fr.bruju.rmeventreader.implementation.formulatracker.formule.formule.Resultat;
import fr.bruju.rmeventreader.implementation.formulatracker.formule.valeur.VStatistique;
import fr.bruju.rmeventreader.implementation.formulatracker.formule.valeur.Valeur;

public class FormulaMaker implements ActionMakerDefalse {

	private Traducteur traducteur;
	private Traiteur traiteurParDefaut;
	private Map<Integer, TraiteurEnregistreur> traiteursSpeciaux;
	private EtatMemoire etat;

	public FormulaMaker(Personnages contexte) {
		Map<Integer, Valeur> variablesExistantes = new HashMap<>();
		Map<Integer, Bouton> interrupteursExistants = new HashMap<>();

		traiteursSpeciaux = fixerPersonnages(contexte, variablesExistantes, interrupteursExistants);

		etat = new EtatMemoire(variablesExistantes, interrupteursExistants);

		traducteur = new Traducteur();
		traiteurParDefaut = new Traiteur();
	}

	private Map<Integer, TraiteurEnregistreur> fixerPersonnages(Personnages contexte,
			Map<Integer, Valeur> variablesExistantes, Map<Integer, Bouton> interrupteursExistants) {
		Map<Integer, TraiteurEnregistreur> traiteursSpeciaux = new HashMap<>();

		contexte.getPersonnages().stream().flatMap(p -> p.getStatistiques().values().stream()).forEach(stat -> {
			variablesExistantes.put(stat.getPosition(), new VStatistique(stat));
			traiteursSpeciaux.put(stat.getPosition(), new TraiteurEnregistreur(stat));
		});

		return traiteursSpeciaux;
	}

	// Entrées / Sorties

	public Resultat getResultat() {
		Resultat resultat = new Resultat();

		traiteursSpeciaux.forEach((numeroVariable, traiteur) -> {
			resultat.integrer(traiteur.getStat(), traiteur.getFormules());
		});

		return resultat;
	}

	private Traiteur getTraiteur(int numVariable) {
		Traiteur t = traiteursSpeciaux.get(numVariable);
		return t != null ? t : traiteurParDefaut;
	}

	/* ============
	 * ACTION MAKER
	 * ============ */

	// CHANGEMENTS DE VALEUR

	// VARIABLE

	@Override
	public void changeVariable(Variable variable, Operator operator, ValeurFixe returnValue) {
		Integer numeroDeVariable = variable.get();
		Valeur vDroite = traducteur.getValue(returnValue);
		Traiteur traiteur = getTraiteur(variable.get());

		traiteur.changeVariable(numeroDeVariable, operator, vDroite);
	}

	@Override
	public void changeVariable(Variable variable, Operator operator, ValeurAleatoire returnValue) {
		Integer numeroDeVariable = variable.get();
		Valeur vDroite = traducteur.getValue(returnValue);
		Traiteur traiteur = getTraiteur(variable.get());

		traiteur.changeVariable(numeroDeVariable, operator, vDroite);
	}

	@Override
	public void changeVariable(Variable variable, Operator operator, Variable returnValue) {
		Integer numeroDeVariable = variable.get();
		Valeur vDroite = etat.getVariable(returnValue.get());
		Traiteur traiteur = getTraiteur(variable.get());

		traiteur.changeVariable(numeroDeVariable, operator, vDroite);
	}

	// CONDITIONS

	@Override
	public boolean condOnSwitch(int number, boolean valeur) {
		Bouton interrupteur = etat.getInterrupteur(number);

		traiteurParDefaut.condOnSwitch(interrupteur, valeur);
		return true;
	}

	@Override
	public boolean condOnEquippedItem(int heroId, int itemId) {
		traiteurParDefaut.condOnEquippedItem(heroId, itemId);
		return true;
	}

	@Override
	public boolean condOnVariable(int leftOperandValue, Operator operatorValue, ValeurFixe returnValue) {
		Valeur vGauche = etat.getVariable(leftOperandValue);
		Valeur vDroite = traducteur.getValue(returnValue);

		traiteurParDefaut.condOnVariable(vGauche, operatorValue, vDroite);
		return true;
	}

	@Override
	public void condElse() {
		traiteurParDefaut.condElse();
	}

	@Override
	public void condEnd() {
		traiteurParDefaut.condEnd();
	}

	// Traducteur

	public Valeur getVariable(int numeroVariable) {
		return etat.getVariable(numeroVariable);
	}

	public Bouton getInterrupteur(int numero) {
		return etat.getInterrupteur(numero);
	}

	/* =========================
	 * TRAITEMENT DES EVENEMENTS
	 * ========================= */

	public class Traiteur {
		public void changeVariable(Integer variable, Operator operator, Valeur vDroite) {
			etat.affecterVariable(variable, operator, vDroite);
		}

		public void condOnSwitch(Bouton interrupteur, boolean valeur) {
			Condition condition = traducteur.getConditionSwitch(interrupteur, valeur);
			etat = etat.creerFils(condition);
		}

		public void condOnEquippedItem(int heros, int objet) {
			Condition condition = traducteur.getConditionObjetEquipe(heros, objet);
			etat = etat.creerFils(condition);
		}

		public void condOnVariable(Valeur vGauche, Operator operateur, Valeur vDroite) {
			Condition condition = traducteur.getConditionVariable(vGauche, operateur, vDroite);
			etat = etat.creerFils(condition);
		}

		public void condElse() {
			etat = etat.getPetitFrere();
		}

		public void condEnd() {
			etat = etat.revenirAuPere();
		}
	}

	public class TraiteurEnregistreur extends Traiteur {
		private Statistique stat;
		private List<FormuleDeDegats> formules;

		public TraiteurEnregistreur(Statistique stat) {
			this.stat = stat;
			formules = new ArrayList<>();
		}

		public Statistique getStat() {
			return stat;
		}

		public List<FormuleDeDegats> getFormules() {
			return formules;
		}

		@Override
		public void changeVariable(Integer variable, Operator operator, Valeur vDroite) {
			formules.add(new ConditionVersValeur(operator, etat.construireListeDeConditions(), vDroite));
			super.changeVariable(variable, operator, vDroite);
		}

	}

}
