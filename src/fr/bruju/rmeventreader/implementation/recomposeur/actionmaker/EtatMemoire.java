package fr.bruju.rmeventreader.implementation.recomposeur.actionmaker;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import fr.bruju.rmeventreader.actionmakers.actionner.Operator;
import fr.bruju.rmeventreader.implementation.recomposeur.composant.bouton.Bouton;
import fr.bruju.rmeventreader.implementation.recomposeur.composant.bouton.BoutonEntree;
import fr.bruju.rmeventreader.implementation.recomposeur.composant.bouton.BoutonVariadique;
import fr.bruju.rmeventreader.implementation.recomposeur.composant.condition.Condition;
import fr.bruju.rmeventreader.implementation.recomposeur.composant.valeur.Valeur;
import fr.bruju.rmeventreader.implementation.recomposeur.composant.valeur.ValeurEntree;
import fr.bruju.rmeventreader.implementation.recomposeur.composant.valeur.ValeurVariadique;

public class EtatMemoire {
	/* ==================
	 * ETAT DE LA MEMOIRE
	 * ================== */

	/** Etat mémoire père */
	private EtatMemoire pere;

	/** Contenu des variables */
	private Map<Integer, ValeurVariadique> variables;
	/** Contenu des interrupteusr*/
	private Map<Integer, BoutonVariadique> interrupteurs;

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
		this.interrupteurs = new HashMap<>();
		this.pere = null;
	}

	/**
	 * Construit un état mémoire ayant le père donné
	 * @param pere Le père de cet état
	 */
	public EtatMemoire(EtatMemoire pere) {
		this.pere = pere;
		this.variables = new HashMap<>();
		this.interrupteurs = new HashMap<>();
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
		return extraireDonnee(this, idVariable, etat -> etat.variables, numero -> new ValeurEntree(numero));
	}


	/**
	 * Donne l'état mémoire de l'interrupteur
	 * @param idVariable Le numéro de l'interrupteur
	 * @return La valeur contenue dans l'interrupteur
	 */
	public Bouton getInterrupteur(int idSwitch) {
		return extraireDonnee(this, idSwitch, etat -> etat.interrupteurs, numero -> new BoutonEntree(numero));
	}

	/**
	 * Extrait l'état mémoire actuel d'une donnée
	 * @param courant L'état mémoire ayant appellé la fonction
	 * @param numero Le numéro de la donnée dans la base
	 * @param fonctionDacces La fonction qui associe à un état mémoire la table des données
	 * @param fonctionDeCreation Fonction qui permet de crée un nouvel élément de base de cette donnée
	 * @return La donnée demandée, qui peut éventuellement avoir été crée si c'est sa première utilisation.
	 */
	private static <T> T extraireDonnee(EtatMemoire courant, Integer numero,
			Function<EtatMemoire, Map<Integer, ? extends T>> fonctionDacces, Function<Integer, T> fonctionDeCreation) {
		T donnee;
		while (true) {
			donnee = fonctionDacces.apply(courant).get(numero);

			if (donnee == null) {
				if (courant.pere == null) {
					return fonctionDeCreation.apply(numero);
				} else {
					courant = courant.pere;
				}
			} else {
				return donnee;
			}
		}
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
		combienrInterrupteurs();
		

		// Supression des enfants
		this.condition = null;
		this.filsGauche = null;
		this.filsDroit = null;
	}
	
	
	private void combinerValeurs() {
		
		
		
		
	}

	private void combienrInterrupteurs() {
	}

	
	public void affecterVariable(Integer variable, Operator operator, Valeur vDroite) {
		
		
		
	}


}
