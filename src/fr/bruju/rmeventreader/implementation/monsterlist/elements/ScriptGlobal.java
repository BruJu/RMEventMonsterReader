package fr.bruju.rmeventreader.implementation.monsterlist.elements;

import static fr.bruju.rmeventreader.implementation.monsterlist.elements.ContexteElementaire.ELEMENTS;
import static fr.bruju.rmeventreader.implementation.monsterlist.elements.ContexteElementaire.PARTIES;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Consumer;

import fr.bruju.rmeventreader.actionmakers.actionner.ActionMakerDefalse;
import fr.bruju.rmeventreader.actionmakers.actionner.Interpreter;
import fr.bruju.rmeventreader.actionmakers.actionner.Operator;
import fr.bruju.rmeventreader.actionmakers.donnees.ValeurFixe;
import fr.bruju.rmeventreader.actionmakers.donnees.Variable;
import fr.bruju.rmeventreader.filereader.LigneNonReconnueException;
import fr.bruju.rmeventreader.implementation.monsterlist.actionmaker.StackedActionMaker;
import fr.bruju.rmeventreader.implementation.monsterlist.manipulation.ConditionOnMonsterId;
import fr.bruju.rmeventreader.implementation.monsterlist.metier.Contexte;
import fr.bruju.rmeventreader.implementation.monsterlist.metier.Donnees;
import fr.bruju.rmeventreader.implementation.monsterlist.metier.MonsterDatabase;
import fr.bruju.rmeventreader.implementation.monsterlist.metier.Monstre;

public class ScriptGlobal extends StackedActionMaker<Monstre> {
	/* =============
	 * SCRIPT GLOBAL
	 * ============= */

	// -- Constantes

	/** ID de la variable contenant l'id du monstre ciblé */
	private int ID_VARIABLE_MONSTRE_CIBLE;
	/** ID de l'évènement contenant les sous fonctions tockées par actionsPage */
	private int EVENT_SOUS_FONCTIONS;

	// -- Attributs

	/** Base de données modifiée */
	private MonsterDatabase bdd;
	/** Contexte élémentaire (contient les associations nom - id variable par exemple */
	private ContexteElementaire contexte;
	/** Liste des pages de l'évènement EVENT_SOUS_FONCTIONS stockées avec les actions correspondant */
	private ArrayList<ActionPage> actionsPage = new ArrayList<>();

	/**
	 * Crée un gestionnaire de script d'affectations des résistances élémentaires
	 * 
	 * @param bdd La base de données de monstre
	 * @param contexteBase Le contexte de base (fichier Parametres.txt)
	 * @param contexte Le contexte élémentaire (fichier Resistances.txt)
	 */
	public ScriptGlobal(MonsterDatabase bdd, Contexte contexteBase, ContexteElementaire contexte) {
		this.bdd = bdd;
		this.contexte = contexte;

		remplirConstantes(contexteBase);
	}

	/** Extrait du contexte général les constantes */
	private void remplirConstantes(Contexte contexteBase) {
		ID_VARIABLE_MONSTRE_CIBLE = contexteBase.getVariable("Elements_VariableMonstreCible");
		EVENT_SOUS_FONCTIONS = contexteBase.getVariable("Elements_EventSousFonction");
	}

	/* ===================
	 * ACTION MAKER A PILE
	 * =================== */
	
	/* -----------------------
	 * Extraction des monstres
	 * ----------------------- */

	@Override
	protected Collection<Monstre> getAllElements() {
		return bdd.extractMonsters();
	}
	

	/* ----------------------------
	 * Initialisation / Terminaison
	 * ---------------------------- */

	// Cette partie se base sur des connaissances sur le jeu de données
	
	@Override
	public void getComment(String str) {
		if (str.equals("Mettre à 0 les résistances élémentaires")) {
			// Initialisation
			bdd.extractMonsters().forEach(this::initialiserMonstre);
		}

		if (str.equals("REHAUSSER LES RESISTANCES")) {
			// Fin - Application manuelle du calcul de réhaussement des résistances
			bdd.extractMonsters().forEach(this::finaliserMonstre);
		}
	}
	
	/**
	 * Initialise les données qui seront enregistrées dans cette classe
	 * @param monstre Le monstre à initialiser
	 */
	private void initialiserMonstre(Monstre monstre) {
		monstre.donnees.put(ContexteElementaire.ELEMENTS,
				new Donnees<Integer>(monstre, contexte.getElements(), 0, v -> v.toString()));
		monstre.donnees.put(ContexteElementaire.PARTIES,
				new Donnees<Boolean>(monstre, contexte.getParties(), false, v -> v ? "x" : "_"));
	}

	/**
	 * Finalise le monstre en appliquant les modifications faites à la fin pour réhausser les résistances selon le
	 * niveau.
	 * @param monstre Le monstre à modifier
	 */
	private void finaliserMonstre(Monstre monstre) {
		Collection<String> elements = contexte.getElements();

		int bonusCalc = monstre.accessInt(Monstre.STATS).get("Niveau");
		bonusCalc = (bonusCalc / 7) * 5;

		int bonus = bonusCalc; // Java refuse de prendre bonusCalc directement

		monstre.accessInt(ContexteElementaire.ELEMENTS).compute("Physique", (n, v) -> v - bonus / 2);

		elements.stream().filter(element -> !element.equals("Physique")).forEach(
				element -> monstre.accessInt(ContexteElementaire.ELEMENTS).compute(element, (n, v) -> v - bonus));
	}
	
	/* -------------------------
	 * Modification des monstres
	 * ------------------------- */

	@Override
	public void changeSwitch(Variable interrupteur, boolean value) {
		String nom = contexte.getPartie(interrupteur.idVariable);

		this.getElementsFiltres().forEach(monstre -> monstre.accessBool(PARTIES).set(nom, value));
	}

	@Override
	public void changeVariable(Variable variable, Operator operator, ValeurFixe returnValue) {
		String nom = contexte.getElement(variable.idVariable);

		this.getElementsFiltres().forEach(monstre -> monstre.accessInt(ELEMENTS).compute(nom,
				(n, ex) -> operator.compute(ex, returnValue.valeur)));
	}

	@Override
	public void callMapEvent(int eventNumber, int eventPage) {
		if (eventNumber != EVENT_SOUS_FONCTIONS) {
			throw new LigneNonReconnueException("Script général : appel d'évènement sur la carte sur " + eventNumber);
		}

		assurerExistance(eventPage);

		this.getElementsFiltres().forEach(actionsPage.get(eventPage - 1)::appliquer);
	}

	/**
	 * Lit la page de l'évènement sur la carte si ça n'a pas été fait
	 * 
	 * @param eventPage Le numéro de la page à charger
	 */
	private void assurerExistance(int eventPage) {
		while (actionsPage.size() <= eventPage) {
			actionsPage.add(null);
		}

		if (actionsPage.get(eventPage - 1) == null) {
			Page p = new Page();

			try {
				new Interpreter(p).inputFile(new File(ContexteElementaire.RESSOURCES_PREFIXE + eventPage + ".txt"));
			} catch (IOException e) {
				e.printStackTrace();
			}

			actionsPage.set(eventPage - 1, p.getResult());
		}
	}

	/* -----------------------------------
	 * Conditions sur le numéro du monstre
	 * ----------------------------------- */
	
	@Override
	public boolean condOnVariable(int leftOperandValue, Operator operatorValue, ValeurFixe returnValue) {
		if (leftOperandValue != ID_VARIABLE_MONSTRE_CIBLE) {
			throw new LigneNonReconnueException("Script général : condition sur " + leftOperandValue);
		}

		this.conditions.push(new ConditionOnMonsterId(true, operatorValue, returnValue.valeur));
		return true;
	}
	
	
	/* ------------
	 * Sous routine
	 * ------------ */

	@Override
	public void callCommonEvent(int eventNumber) {
		Interpreter interpreter = new Interpreter(this);

		try {
			interpreter.inputFile(new File(ContexteElementaire.SECONDFICHIER));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	

	/* ==================
	 * SOUS ACTION MAKERS
	 * ================== */

	/** Application des effets d'un appel à un évènement sur la carte */
	public static interface ActionPage {
		/** Applique les effets d'une page sur le monstre */
		public void appliquer(Monstre monstre);
	}

	/**
	 * Cette sous classe construit la liste des actions à réaliser lorsqu'une sous-fonction est appelée.
	 * <p>
	 * Le script appelle régulièrement des évènements qui se servent à appliquer des résistances liées à des types de
	 * monstres. Cette classe lit les modifications faites et les donne sous la forme d'un objet à appliquer, ce qui
	 * permet de gagner légèrement en performances.
	 * <p>
	 * Ces pages ne contiennent que des instruction du type "modifier une variable" ou "modifier un interrupteur".
	 *
	 */
	public class Page implements ActionMakerDefalse {
		/** Liste des actions réaliser vues */
		private List<Consumer<Monstre>> actionsARealiser = new ArrayList<>();

		/**
		 * Donne le résultat de l'exécution
		 * 
		 * @return Une liste d'actions à réaliser sur un monstre qui appellerait la page
		 */
		public ActionPage getResult() {
			return monstre -> actionsARealiser.forEach(action -> action.accept(monstre));
		}

		@Override
		public void changeSwitch(Variable interrupteur, boolean value) {
			String nomPartie = contexte.getPartie(interrupteur.get());

			actionsARealiser.add(monstre -> monstre.accessBool(ContexteElementaire.PARTIES).set(nomPartie, value));
		}

		@Override
		public void changeVariable(Variable variable, Operator operator, ValeurFixe returnValue) {
			String nomElement = contexte.getElement(variable.idVariable);

			actionsARealiser.add(monstre -> monstre.accessInt(ContexteElementaire.ELEMENTS).compute(nomElement,
					(nom, valeur) -> operator.compute(valeur, returnValue.valeur)));
		}

		@Override
		public void condElse() {
			// Pas de condition
		}

		@Override
		public void condEnd() {
			// Pas de condition
		}
	}
}
