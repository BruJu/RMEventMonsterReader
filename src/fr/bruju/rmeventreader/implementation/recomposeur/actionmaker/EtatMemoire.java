package fr.bruju.rmeventreader.implementation.recomposeur.actionmaker;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;

import fr.bruju.rmeventreader.actionmakers.actionner.Operator;
import fr.bruju.rmeventreader.implementation.recomposeur.composant.composantvariadique.Calcul;
import fr.bruju.rmeventreader.implementation.recomposeur.composant.composantvariadique.Conditionnelle;
import fr.bruju.rmeventreader.implementation.recomposeur.composant.condition.Condition;
import fr.bruju.rmeventreader.implementation.recomposeur.composant.valeur.Valeur;
import fr.bruju.rmeventreader.implementation.recomposeur.composant.valeur.Entree;
import fr.bruju.rmeventreader.implementation.recomposeur.composant.valeur.Algorithme;

public class EtatMemoire {
	private static final int OFFSET_SWITCH = 5000;
	
	/* ==================
	 * ETAT DE LA MEMOIRE
	 * ================== */

	/** Etat mémoire père */
	private EtatMemoire pere;

	/** Contenu des variables */
	private Map<Integer, Algorithme> variables;

	/** Condition ayant mené à la création des fils */
	private Condition condition = null;
	/** Fils donnant l'état mémoire si la condition est respectée */
	private EtatMemoire filsGauche = null;
	/** Fils donnant l'état mémoire si la condition est fausse */
	private EtatMemoire filsDroit = null;

	/**
	 * Construit un état mémoire avec des valeurs pré existantes
	 * @param variablesExistantes Etat des variables
	 * @param interrupteursExistants Etat des interrupteurs
	 */
	public EtatMemoire() {
		this.variables = new HashMap<>();;
		this.pere = null;
	}

	/**
	 * Construit un état mémoire ayant le père donné
	 * @param pere Le père de cet état
	 */
	public EtatMemoire(EtatMemoire pere) {
		this.pere = pere;
		this.variables = new HashMap<>();
	}

	/* =================
	 * STRUCTURE D'ARBRE
	 * ================= */

	/**
	 * Crée deux fils par rapport à la condition donnée et renvoie le fils de l'état mémoire si la condition est vraie.
	 * @param condition La condition à respecer
	 * @return Le fils gauche
	 */
	public EtatMemoire creerFils(Condition condition) {
		this.condition = condition;
		filsGauche = new EtatMemoire(this);
		filsDroit = new EtatMemoire(this);

		return filsGauche;
	}

	/**
	 * Permet d'accéder au petit frère (fils droit du père) de cet état.
	 * 
	 * @return this.pere.filsDroit
	 */
	public EtatMemoire getPetitFrere() {
		return this.pere.filsDroit;
	}

	/**
	 * Permet de savoir si on est un fils gauche
	 * @return this == pere.filsGauche
	 */
	private boolean estFilsGauche() {
		return this == pere.filsGauche;
	}
	
	/* ==================
	 * ACCES A LA MEMOIRE
	 * ================== */

	/**
	 * Donne l'état mémoire de la variable
	 * @param idVariable Le numéro de la variable
	 * @return La valeur contenue dans la variable
	 */
	public Valeur getVariable(Integer idVariable) {
		EtatMemoire courant = this;
		Valeur donnee;
		
		while (true) {
			donnee = courant.variables.get(idVariable);

			if (donnee == null) {
				if (courant.pere == null) {
					return new Entree(idVariable);
				} else {
					courant = courant.pere;
				}
			} else {
				return donnee;
			}
		}
	}


	/**
	 * Donne l'état mémoire de l'interrupteur
	 * @param idVariable Le numéro de l'interrupteur
	 * @return La valeur contenue dans l'interrupteur
	 */
	public Valeur getInterrupteur(int idSwitch) {
		return getVariable(idSwitch + OFFSET_SWITCH);
	}

	/**
	 * Permet de détruire cet état et son frère en intégrant leurs données dans le père
	 * @return L'état mémoire père
	 */
	public EtatMemoire revenirAuPere() {
		pere.integrerFils();
		return pere;
	}

	/**
	 * Fusionne les données des fils dans cet état.
	 */
	private void integrerFils() {
		// Combinaisons
		combinerValeurs();

		// Supression des enfants
		this.condition = null;
		this.filsGauche = null;
		this.filsDroit = null;
	}
	
	
	private void combinerValeurs() {
		Set<Integer> listeDesVariables = new HashSet<>();
		
		this.filsGauche.variables.keySet().addAll(listeDesVariables);
		this.filsDroit.variables.keySet().addAll(listeDesVariables);
		
		listeDesVariables.forEach(idVariable -> {
			// Création de la conditionnelle
			Algorithme g = filsGauche.variables.getOrDefault(idVariable, new Algorithme());
			Algorithme d = filsDroit.variables.getOrDefault(idVariable, new Algorithme());
			
			Conditionnelle conditionnelle = new Conditionnelle(condition, g, d);
			
			// Injection dans la base de données
			Valeur initial = getVariable(idVariable);
			Algorithme a = initial.toAlgorithme();
			
			a = new Algorithme(a, conditionnelle);
			
			// Mise en mémoire
			this.variables.put(idVariable, a);
		});
	}


	
	public void affecterVariable(Integer variable, Operator operator, Valeur vDroite) {
		if (operator == Operator.AFFECTATION) {
			this.variables.put(variable, vDroite.toAlgorithme());
		} else {
			Calcul calcul = new Calcul(operator, vDroite);
			variables.compute(variable, (id, algoPresent) -> new Algorithme(algoPresent, calcul));
		}
	}


}
