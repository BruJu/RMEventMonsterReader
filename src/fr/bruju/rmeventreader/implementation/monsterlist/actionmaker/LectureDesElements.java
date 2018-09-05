package fr.bruju.rmeventreader.implementation.monsterlist.actionmaker;

import static fr.bruju.rmeventreader.implementation.monsterlist.contexte.ContexteElementaire.ELEMENTS;
import static fr.bruju.rmeventreader.implementation.monsterlist.contexte.ContexteElementaire.PARTIES;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Consumer;

import fr.bruju.rmeventreader.actionmakers.actionner.ActionMakerDefalse;
import fr.bruju.rmeventreader.actionmakers.actionner.Operator;
import fr.bruju.rmeventreader.actionmakers.donnees.ValeurFixe;
import fr.bruju.rmeventreader.actionmakers.donnees.Variable;
import fr.bruju.rmeventreader.actionmakers.xml.InterpreterMapXMLCache;
import fr.bruju.rmeventreader.filereader.LigneNonReconnueException;
import fr.bruju.rmeventreader.implementation.monsterlist.contexte.Contexte;
import fr.bruju.rmeventreader.implementation.monsterlist.contexte.ContexteElementaire;
import fr.bruju.rmeventreader.implementation.monsterlist.manipulation.Condition;
import fr.bruju.rmeventreader.implementation.monsterlist.manipulation.ConditionOnMonsterId;
import fr.bruju.rmeventreader.implementation.monsterlist.metier.MonsterDatabase;
import fr.bruju.rmeventreader.implementation.monsterlist.metier.Monstre;

@SuppressWarnings("unused")
public abstract class LectureDesElements extends StackedActionMaker<Monstre> {
//	/* =============
//	 * SCRIPT GLOBAL
//	 * ============= */
//
//	// -- Constantes
//
//	/** ID de la variable contenant l'id du monstre ciblé */
//	private final int ID_VARIABLE_MONSTRE_CIBLE;
//	/** ID de l'évènement contenant les sous fonctions tockées par actionsPage */
//	private final int EVENT_SOUS_FONCTIONS;
//
//	// -- Attributs
//
//	/** Base de données modifiée */
//	private MonsterDatabase bdd;
//	/** Contexte élémentaire (contient les associations nom - id variable par exemple */
//	private ContexteElementaire contexte;
//	/** Liste des pages de l'évènement EVENT_SOUS_FONCTIONS stockées avec les actions correspondant */
//	private ArrayList<ActionPage> actionsPage = new ArrayList<>();
//	private boolean estFini = false;
//
//	/**
//	 * Crée un gestionnaire de script d'affectations des résistances élémentaires
//	 * 
//	 * @param bdd La base de données de monstre
//	 * @param contexteBase Le contexte de base (fichier Parametres.txt)
//	 * @param contexte Le contexte élémentaire (fichier Resistances.txt)
//	 */
//	public LectureDesElements(MonsterDatabase bdd, Contexte contexteBase, ContexteElementaire contexte) {
//		this.bdd = bdd;
//		this.contexte = contexte;
//
//		// Extrait du contexte général les constantes
//		ID_VARIABLE_MONSTRE_CIBLE = contexteBase.getVariable("Elements_VariableMonstreCible");
//		EVENT_SOUS_FONCTIONS = contexteBase.getVariable("Elements_EventSousFonction");
//	}
//
//	/* ===================
//	 * ACTION MAKER A PILE
//	 * =================== */
//
//	/* -----------------------
//	 * Extraction des monstres
//	 * ----------------------- */
//
//	@Override
//	protected Collection<Monstre> getAllElements() {
//		return bdd.extractMonsters();
//	}
//
//	/* -------------------------
//	 * Modification des monstres
//	 * ------------------------- */
//
//	@Override
//	public void getComment(String str) {
//		if (str.equals("Fin")) {
//			estFini  = true;
//			this.conditions.push(new Condition<Monstre>() {
//
//				@Override
//				public void revert() {
//				}
//
//				@Override
//				public boolean filter(Monstre element) {
//					return false;
//				}
//				
//				
//			});
//		}
//	}
//
//	@Override
//	public void changeSwitch(Variable interrupteur, boolean value) {
//		String nom = contexte.getPartie(interrupteur.idVariable);
//
//		this.getElementsFiltres().forEach(monstre -> monstre.accessBool(PARTIES).set(nom, value));
//	}
//
//	@Override
//	public void changeVariable(Variable variable, Operator operator, ValeurFixe returnValue) {
//		String nom = contexte.getElement(variable.idVariable);
//
//		if (nom == null)
//			return;
//
//		this.getElementsFiltres().forEach(monstre -> monstre.accessInt(ELEMENTS).compute(nom,
//				(n, ex) -> operator.compute(ex, returnValue.valeur)));
//	}
//
//	@Override
//	public void callMapEvent(int eventNumber, int eventPage) {
//		if (eventNumber != EVENT_SOUS_FONCTIONS) {
//			throw new LigneNonReconnueException("Script général : appel d'évènement sur la carte sur " + eventNumber);
//		}
//
//		assurerExistance(eventPage);
//
//		this.getElementsFiltres().forEach(actionsPage.get(eventPage - 1)::appliquer);
//	}
//
//	/**
//	 * Lit la page de l'évènement sur la carte si ça n'a pas été fait
//	 * 
//	 * @param eventPage Le numéro de la page à charger
//	 */
//	private void assurerExistance(int eventPage) {
//		while (actionsPage.size() <= eventPage) {
//			actionsPage.add(null);
//		}
//
//		if (actionsPage.get(eventPage - 1) == null) {
//			Page p = new Page();
//
//			new InterpreterMapXMLCache(p).inputFile(53, EVENT_SOUS_FONCTIONS, eventPage);
//				
//			actionsPage.set(eventPage - 1, p.getResult());
//		}
//	}
//
//	/* -----------------------------------
//	 * Conditions sur le numéro du monstre
//	 * ----------------------------------- */
//
//	@Override
//	public boolean condOnVariable(int leftOperandValue, Operator operatorValue, ValeurFixe returnValue) {
//		if (leftOperandValue != ID_VARIABLE_MONSTRE_CIBLE) {
//			if (leftOperandValue == 42) {
//				this.conditions.push(new Condition<Monstre>() {
//					boolean b = false;
//					
//					@Override
//					public void revert() {
//						b = !b;
//					}
//
//					@Override
//					public boolean filter(Monstre element) {
//						return b;
//					}
//					
//					
//				});
//
//				return true;
//			} else {
//				this.conditions.push(new Condition<Monstre>() {
//
//					@Override
//					public void revert() {
//					}
//
//					@Override
//					public boolean filter(Monstre element) {
//						return false;
//					}
//					
//					
//				});
//				return true;
//			}
//			
//			
//			
//		}
//
//		this.conditions.push(new ConditionOnMonsterId(true, operatorValue, returnValue.valeur));
//		return true;
//	}
//
//	/* ------------
//	 * Sous routine
//	 * ------------ */
//
//	@Override
//	public void callCommonEvent(int eventNumber) {
//		if (estFini)
//			return;
//		
//		InterpreterMapXMLCache intreprete = new InterpreterMapXMLCache(this);
//		
//		intreprete.inputFile(-1, eventNumber, -1);
//	}
//
//	/* ==================
//	 * SOUS ACTION MAKERS
//	 * ================== */
//
//	/** Application des effets d'un appel à un évènement sur la carte */
//	public static interface ActionPage {
//		/** Applique les effets d'une page sur le monstre */
//		public void appliquer(Monstre monstre);
//	}
//
//	/**
//	 * Cette sous classe construit la liste des actions à réaliser lorsqu'une sous-fonction est appelée.
//	 * <p>
//	 * Le script appelle régulièrement des évènements qui se servent à appliquer des résistances liées à des types de
//	 * monstres. Cette classe lit les modifications faites et les donne sous la forme d'un objet à appliquer, ce qui
//	 * permet de gagner légèrement en performances.
//	 * <p>
//	 * Ces pages ne contiennent que des instruction du type "modifier une variable" ou "modifier un interrupteur".
//	 *
//	 */
//	public class Page implements ActionMakerDefalse {
//		/** Liste des actions réaliser vues */
//		private List<Consumer<Monstre>> actionsARealiser = new ArrayList<>();
//
//		/**
//		 * Donne le résultat de l'exécution
//		 * 
//		 * @return Une liste d'actions à réaliser sur un monstre qui appellerait la page
//		 */
//		public ActionPage getResult() {
//			return monstre -> actionsARealiser.forEach(action -> action.accept(monstre));
//		}
//
//		@Override
//		public void changeSwitch(Variable interrupteur, boolean value) {
//			String nomPartie = contexte.getPartie(interrupteur.get());
//
//			actionsARealiser.add(monstre -> monstre.accessBool(ContexteElementaire.PARTIES).set(nomPartie, value));
//		}
//
//		@Override
//		public void changeVariable(Variable variable, Operator operator, ValeurFixe returnValue) {
//			String nomElement = contexte.getElement(variable.idVariable);
//
//			actionsARealiser.add(monstre -> monstre.accessInt(ContexteElementaire.ELEMENTS).compute(nomElement,
//					(nom, valeur) -> operator.compute(valeur, returnValue.valeur)));
//		}
//
//		@Override
//		public void condElse() {
//			// Pas de condition
//		}
//
//		@Override
//		public void condEnd() {
//			// Pas de condition
//		}
//	}
}
