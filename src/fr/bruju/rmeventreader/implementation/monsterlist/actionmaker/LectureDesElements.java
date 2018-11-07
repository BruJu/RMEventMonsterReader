package fr.bruju.rmeventreader.implementation.monsterlist.actionmaker;

import static fr.bruju.rmeventreader.implementation.monsterlist.contexte.ContexteElementaire.ELEMENTS;
import static fr.bruju.rmeventreader.implementation.monsterlist.contexte.ContexteElementaire.PARTIES;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Consumer;

import fr.bruju.rmdechiffreur.controlleur.ExtChangeVariable;
import fr.bruju.rmdechiffreur.controlleur.ExtCondition;
import fr.bruju.rmdechiffreur.modele.Comparateur;
import fr.bruju.rmdechiffreur.modele.FixeVariable;
import fr.bruju.rmdechiffreur.modele.OpMathematique;
import fr.bruju.rmdechiffreur.modele.ValeurFixe;
import fr.bruju.rmdechiffreur.modele.Variable;
import fr.bruju.rmeventreader.implementation.LigneNonReconnueException;
import fr.bruju.rmeventreader.implementation.monsterlist.contexte.ContexteElementaire;
import fr.bruju.rmeventreader.implementation.monsterlist.manipulation.ConditionFausse;
import fr.bruju.rmeventreader.implementation.monsterlist.manipulation.ConditionOnMonsterId;
import fr.bruju.rmeventreader.implementation.monsterlist.manipulation.ConditionVariable;
import fr.bruju.rmeventreader.implementation.monsterlist.metier.MonsterDatabase;
import fr.bruju.rmeventreader.implementation.monsterlist.metier.Monstre;

import static fr.bruju.rmeventreader.ProjetS.PROJET;

public class LectureDesElements extends ExecuteurAFiltre<Monstre> implements ExtChangeVariable, ExtCondition {
	/* =============
	 * SCRIPT GLOBAL
	 * ============= */

	// -- Constantes

	/** ID de la variable contenant l'id du monstre ciblé */
	private final int ID_VARIABLE_MONSTRE_CIBLE = 552;
	/** ID de l'évènement contenant les sous fonctions tockées par actionsPage */
	private final int EVENT_SOUS_FONCTIONS = 99;

	// -- Attributs

	/** Base de données modifiée */
	private MonsterDatabase bdd;
	/** Contexte élémentaire (contient les associations nom - id variable par exemple */
	private ContexteElementaire contexte;
	/** Liste des pages de l'évènement EVENT_SOUS_FONCTIONS stockées avec les actions correspondant */
	private ArrayList<ActionPage> actionsPage = new ArrayList<>();
	private boolean estFini = false;

	/**
	 * Crée un gestionnaire de script d'affectations des résistances élémentaires
	 * 
	 * @param bdd La base de données de monstre
	 * @param contexte Le contexte élémentaire (fichier Resistances.txt)
	 */
	public LectureDesElements(MonsterDatabase bdd, ContexteElementaire contexte) {
		this.bdd = bdd;
		this.contexte = contexte;
	}

	/* ===================
	 * ACTION MAKER DetecteurDeColissionsDInterrupteurs PILE
	 * =================== */

	/* -----------------------
	 * Extraction des monstres
	 * ----------------------- */

	@Override
	protected Collection<Monstre> getAllElements() {
		return bdd.extractMonsters();
	}
	
	/* -------------------------
	 * Modification des monstres
	 * ------------------------- */


	@Override
	public void Flot_commentaire(String message) {
		if (message.equals("Fin")) {
			estFini = true;
			conditions.push(new ConditionFausse<>());
		}
	}

	@Override
	public void Flot_appelEvenementCarte(FixeVariable evenement, FixeVariable page) {
		if (evenement instanceof Variable) {
			return;
		}
		
		int eventNumber = ((ValeurFixe) evenement).valeur;
		int eventPage = ((ValeurFixe) page).valeur;
		
		if (eventNumber != EVENT_SOUS_FONCTIONS) {
			throw new LigneNonReconnueException("Script général : appel d'évènement sur la carte sur " + eventNumber);
		}

		assurerExistance(eventPage);

		getElementsFiltres().forEach(actionsPage.get(eventPage - 1)::appliquer);
	}

	@Override
	public void Flot_appelEvenementCommun(int numero) {
		if (estFini) {
			return;
		}
		
		PROJET.lireEvenementCommun(this, numero);
	}
	
	@Override
	public void changeSwitch(Variable interrupteur,	boolean nouvelleValeur) {
		String nom = contexte.getPartie(interrupteur.idVariable);

		this.getElementsFiltres().forEach(monstre -> monstre.accessBool(PARTIES).set(nom, nouvelleValeur));
	}

	@Override
	public void affecterVariable(Variable valeurGauche, ValeurFixe valeurDroite) {
		changerVariable(valeurGauche, OpMathematique.AFFECTATION, valeurDroite);
	}

	@Override
	public void changerVariable(Variable variable, OpMathematique operateur, ValeurFixe valeur) {
		String nom = contexte.getElement(variable.idVariable);

		if (nom == null)
			return;
		
		this.getElementsFiltres().forEach(monstre -> monstre.accessInt(ELEMENTS).compute(nom,
				(n, ex) -> operateur.calculer(ex, valeur.valeur)));
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
			
			PROJET.lireEvenement(p, 53, EVENT_SOUS_FONCTIONS, eventPage);
			
			actionsPage.set(eventPage - 1, p.getPage());
		}
	}

	/* -----------------------------------
	 * Conditions sur le numéro du monstre
	 * ----------------------------------- */

	@Override
	public int variableFixe(int variable, Comparateur comparateur, ValeurFixe droite) {
		if (variable == ID_VARIABLE_MONSTRE_CIBLE) {
			this.conditions.push(new ConditionOnMonsterId(true, comparateur, droite.valeur));
			return 0;
		}

		if (variable == 42) {
			this.conditions.push(new ConditionVariable<>(false));
			return 0;
		}
		
		return 3;
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
	public class Page implements ExtChangeVariable {
		/** Liste des actions réaliser vues */
		private List<Consumer<Monstre>> actionsARealiser = new ArrayList<>();

		/**
		 * Donne le résultat de l'exécution
		 * 
		 * @return Une liste d'actions à réaliser sur un monstre qui appellerait la page
		 */
		public ActionPage getPage() {
			return monstre -> actionsARealiser.forEach(action -> action.accept(monstre));
		}

		@Override
		public void changeSwitch(Variable interrupteur, boolean value) {
			String nomPartie = contexte.getPartie(interrupteur.idVariable);

			actionsARealiser.add(monstre -> monstre.accessBool(ContexteElementaire.PARTIES).set(nomPartie, value));
		}
		
		@Override
		public void affecterVariable(Variable valeurGauche, ValeurFixe valeurDroite) {
			changerVariable(valeurGauche, OpMathematique.AFFECTATION, valeurDroite);
		}

		@Override
		public void changerVariable(Variable variable, OpMathematique operateur, ValeurFixe valeurDroite) {
			String nomElement = contexte.getElement(variable.idVariable);
			
			actionsARealiser.add(monstre -> monstre.accessInt(ContexteElementaire.ELEMENTS).compute(nomElement,
					(nom, valeur) -> operateur.calculer(valeur, valeurDroite.valeur)));
		}

		@Override
		public boolean getBooleenParDefaut() {
			return false;
		}
	}

	@Override
	public boolean getBooleenParDefaut() {
		return false;
	}
}
