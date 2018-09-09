package fr.bruju.rmeventreader.implementation.formulatracker.actionmaker;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BinaryOperator;
import java.util.function.Function;

import fr.bruju.rmeventreader.actionmakers.executeur.modele.OpMathematique;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.bouton.BBase;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.bouton.BTernaire;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.bouton.Bouton;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.condition.Condition;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.valeur.VBase;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.valeur.VCalcul;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.valeur.VTernaire;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.valeur.Valeur;
import fr.bruju.rmeventreader.utilitaire.Pair;

/**
 * Un état mémoire représente l'état de la mémoire (contenu des variables et des interrupteurs) à un moment donné.
 * <p>
 * Les états se structurent sous forme d'arbre.
 * @author Bruju
 *
 */
public class EtatMemoire {
	/* ==================
	 * ETAT DE LA MEMOIRE
	 * ================== */

	/** Etat mémoire père */
	private EtatMemoire pere;

	/** Contenu des variables */
	private Map<Integer, Valeur> variables;
	/** Contenu des interrupteusr*/
	private Map<Integer, Bouton> interrupteurs;

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
	public EtatMemoire(Map<Integer, Valeur> variablesExistantes, Map<Integer, Bouton> interrupteursExistants) {
		this.variables = variablesExistantes;
		this.interrupteurs = interrupteursExistants;
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
		return extraireDonnee(this, idVariable, etat -> etat.variables, numero -> new VBase(numero));
	}


	/**
	 * Donne l'état mémoire de l'interrupteur
	 * @param idVariable Le numéro de l'interrupteur
	 * @return La valeur contenue dans l'interrupteur
	 */
	public Bouton getInterrupteur(int idSwitch) {
		return extraireDonnee(this, idSwitch, etat -> etat.interrupteurs, numero -> new BBase(numero));
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
			Function<EtatMemoire, Map<Integer, T>> fonctionDacces, Function<Integer, T> fonctionDeCreation) {
		T donnee;
		while (true) {
			donnee = fonctionDacces.apply(courant).get(numero);

			if (donnee == null) {
				if (courant.pere == null) {
					fonctionDacces.apply(courant).put(numero, fonctionDeCreation.apply(numero));
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
		combinerDonnees(this, etat -> etat.variables, (v1, v2) -> new VTernaire(condition, v1, v2),
				id -> getVariable(id));
		combinerDonnees(this, etat -> etat.interrupteurs, (v1, v2) -> new BTernaire(condition, v1, v2),
				id -> getInterrupteur(id));

		// Supression des enfants
		this.condition = null;
		this.filsGauche = null;
		this.filsDroit = null;
	}

	/**
	 * Combine les données d'un type dans l'état père
	 * @param pere L'état père
	 * @param fonctionDacces La fonction permettant d'accéder à la carte des données à partir d'un état mémoire
	 * @param fonctionDeFusion La fonction permettant de combinenr deux valeurs dans la mémoire
	 * @param getElementPere La fonction permettant de récupérer la donnée du père
	 */
	private static <T> void combinerDonnees(EtatMemoire pere, Function<EtatMemoire, Map<Integer, T>> fonctionDacces,
			BinaryOperator<T> fonctionDeFusion, Function<Integer, T> getElementPere) {
		Map<Integer, T> mapPere = fonctionDacces.apply(pere);

		// Combinaison des deux fils
		Map<Integer, Pair<T, T>> nouvellesDonnees = new HashMap<>();

		fonctionDacces.apply(pere.filsGauche).forEach((idVariable, valeur) -> nouvellesDonnees.put(idVariable,
				new Pair<>(valeur, getElementPere.apply(idVariable))));

		fonctionDacces.apply(pere.filsDroit)
				.forEach((idVariable, valeur) -> nouvellesDonnees.merge(idVariable,
						new Pair<>(getElementPere.apply(idVariable), valeur),
						(v1, v2) -> new Pair<>(v1.getLeft(), v2.getRight())));

		// Modification du père
		nouvellesDonnees.forEach(
				(idVar, paire) -> mapPere.put(idVar, fonctionDeFusion.apply(paire.getLeft(), paire.getRight())));
	}

	/**
	 * Modifie la variable donnée avec l'opérateur et la valeur donnée
	 * @param variable La variable à modifier
	 * @param operator L'opérateur utilisé
	 * @param vDroite L'opérande droite
	 */
	public void affecterVariable(Integer variable, OpMathematique operator, Valeur vDroite) {
		Valeur vGauche = getVariable(variable);

		if (operator == OpMathematique.AFFECTATION) {
			variables.put(variable, vDroite);
		} else {
			variables.put(variable, new VCalcul(vGauche, operator, vDroite));
		}
	}

	/**
	 * Donne la liste des conditions menant à cet état mémoire
	 * @return La liste des conditions menant à cet état mémoire
	 */
	public List<Condition> construireListeDeConditions() {
		List<Condition> conditions = construireListe(this, etat -> etat.pere.condition,
				etat -> etat.pere.condition.revert());
		Collections.reverse(conditions);
		return conditions;
	}

	/**
	 * Construit une liste en fonction d'un état mémoire jusqu'à qu'il n'ait plus de père
	 * @param etat L'état de départ
	 * @param elementAjouteSiGauche La fonction donnant la valeur à ajouter si on est un fils gauche
	 * @param elementAjouteSiDroite La fonction donnant la valeur à ajouter si on est un fils droit
	 * @return La liste construite
	 */
	private static <T> List<T> construireListe(EtatMemoire etat, Function<EtatMemoire, T> elementAjouteSiGauche,
			Function<EtatMemoire, T> elementAjouteSiDroite) {
		List<T> conditions = new ArrayList<>();

		while (etat.pere != null) {

			conditions
					.add(etat.estFilsGauche() ? elementAjouteSiGauche.apply(etat) : elementAjouteSiDroite.apply(etat));

			etat = etat.pere;
		}

		return conditions;
	}

}
