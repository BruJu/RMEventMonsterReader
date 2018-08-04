package fr.bruju.rmeventreader.implementation.recomposeur.actionmaker;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import fr.bruju.rmeventreader.actionmakers.actionner.ActionMakerDefalse;
import fr.bruju.rmeventreader.actionmakers.actionner.Operator;
import fr.bruju.rmeventreader.actionmakers.donnees.ValeurAleatoire;
import fr.bruju.rmeventreader.actionmakers.donnees.ValeurFixe;
import fr.bruju.rmeventreader.actionmakers.donnees.Variable;
import fr.bruju.rmeventreader.implementation.recomposeur.composant.condition.Condition;
import fr.bruju.rmeventreader.implementation.recomposeur.composant.condition.ConditionArme;
import fr.bruju.rmeventreader.implementation.recomposeur.composant.condition.ConditionValeur;
import fr.bruju.rmeventreader.implementation.recomposeur.composant.valeur.Valeur;
import fr.bruju.rmeventreader.implementation.recomposeur.composant.valeur.ValeurConstante;

/**
 * Constructeur de formules à partir d'un fichier pour donner le contenu des variables trackées en fonction d'autres
 * variables.
 * 
 * @author Bruju
 *
 */
public class ComposeurInitial implements ActionMakerDefalse {
	private final boolean affecterLesAffichage = true;
	
	
	/** Un objet appelé lorsqu'une ligne quelconque est lue */
	private Traiteur traiteurParDefaut;
	
	private Traiteur traiteurSpecial;
	
	/** Association entre numéro de variables et traiteurs à appeler */
	private Set<Integer> variablesSpeciales;
	
	private EtatMemoire etat;

	/**
	 * Construit un constructeur de formules à partir d'une base de personnages
	 * 
	 * @param contexte La base de personnages
	 * @param affecterLesAffichage Si faux, les formules seront plus simples mais moins exactes
	 */
	public ComposeurInitial(List<Integer> variablesAPister) {
		variablesSpeciales = variablesAPister.stream().collect(Collectors.toSet());
		
		etat = new EtatMemoire();
		
		traiteurParDefaut = new Traiteur();
		traiteurSpecial = new TraiteurEnregistreur();
	}

	// Entrées / Sorties

	/**
	 * Donne un objet résultat contenant les résultats de l'exécution du ComposeurInitial
	 */
	public Map<Integer, Valeur> getResultat() {
		Map<Integer, Valeur> resultat = new HashMap<>();
		variablesSpeciales.forEach(id -> resultat.put(id, getVariable(id)));
		return resultat;
	}
	

	/**
	 * Donne le traiteur à utiliser pour le numéro de variable donné
	 * 
	 * @param numVariable Le numéro de variable
	 * @return Le traiteur à utiliser pour cette variable
	 */
	private Traiteur getTraiteur(int numVariable) {
		return (variablesSpeciales.contains(numVariable)) ? traiteurParDefaut : traiteurSpecial;
	}

	/* ============
	 * ACTION MAKER
	 * ============ */

	// CHANGEMENTS DE VALEUR

	// VARIABLE

	@Override
	public void changeVariable(Variable variable, Operator operator, ValeurFixe valeurDroite) {
		Integer numeroDeVariable = variable.get();
		Valeur vDroite = new ValeurConstante(valeurDroite.valeur);
		Traiteur traiteur = getTraiteur(variable.get());

		traiteur.changeVariable(numeroDeVariable, operator, vDroite);
	}

	@Override
	public void changeVariable(Variable variable, Operator operator, ValeurAleatoire valeurDroite) {
		Integer numeroDeVariable = variable.get();
		Valeur vDroite = new fr.bruju.rmeventreader.implementation.recomposeur.composant.valeur.ValeurAleatoire(
				valeurDroite.valeurMin, valeurDroite.valeurMax);
		Traiteur traiteur = getTraiteur(variable.get());

		traiteur.changeVariable(numeroDeVariable, operator, vDroite);
	}

	@Override
	public void changeVariable(Variable variable, Operator operator, Variable returnValue) {
		Integer numeroDeVariable = variable.get();
		Valeur vDroite = getVariable(returnValue.get());
		Traiteur traiteur = getTraiteur(variable.get());

		traiteur.changeVariable(numeroDeVariable, operator, vDroite);
	}

	// CONDITIONS

	@Override
	public boolean condOnSwitch(int number, boolean valeur) {
		etat = etat.creerFils(traiteurParDefaut.condOnSwitch(number, valeur));
		return true;
	}
	
	@Override
	public boolean condOnEquippedItem(int heroId, int itemId) {
		etat = etat.creerFils(traiteurParDefaut.condOnEquippedItem(heroId, itemId));
		return true;
	}

	@Override
	public boolean condOnVariable(int leftOperandValue, Operator operatorValue, ValeurFixe returnValue) {
		Valeur vDroite = new ValeurConstante(returnValue.valeur);
		
		etat = etat.creerFils(traiteurParDefaut.condOnVariable(leftOperandValue, operatorValue, vDroite));
		return true;
	}

	@Override
	public boolean condOnVariable(int leftOperandValue, Operator operatorValue, Variable returnValue) {
		Valeur vDroite = getVariable(returnValue.idVariable);

		etat = etat.creerFils(traiteurParDefaut.condOnVariable(leftOperandValue, operatorValue, vDroite));
		return true;
	}
	
	@Override
	public void condElse() {
		etat = etat.getPetitFrere();
	}

	@Override
	public void condEnd() {
		etat = etat.revenirAuPere();
	}

	// Traducteur

	/**
	 * Donne la valeur contenue dans la variable dont le numéro est donné
	 * 
	 * @param numeroVariable Le numéro de la variable
	 * @return La valeur qu'elle contient
	 */
	public Valeur getVariable(int numeroVariable) {
		return etat.getVariable(numeroVariable);
	}

	/**
	 * Donne la valeur contenue dans l'interrupteur dont le numéro est donné
	 * 
	 * @param numeroVariable Le numéro de l'interrupteur
	 * @return La valeur qu'il contient
	 */
	public Valeur getInterrupteur(int numero) {
		return etat.getInterrupteur(numero);
	}

	/* =========================
	 * TRAITEMENT DES EVENEMENTS
	 * ========================= */

	/**
	 * Traiteur (dans cette implémentation par défaut) des actions à faire lorsqu'une action est reçue. Se contente de
	 * déléguer le traitement à l'état mémoire
	 * 
	 * @author Bruju
	 *
	 */
	private class Traiteur {
		/** Changement de variable */
		public void changeVariable(Integer variable, Operator operator, Valeur vDroite) {
			etat.affecterVariable(variable, operator, vDroite);
		}

		/** Condition sur un interrupteur */
		public Condition condOnSwitch(int number, boolean valeur) {
			return new ConditionValeur(getInterrupteur(number), valeur);
		}

		/** Condition sur un objet équipé */
		public Condition condOnEquippedItem(int heros, int objet) {
			return new ConditionArme(heros, objet);
		}

		/** Condition sur une variable */
		public Condition condOnVariable(int idGauche, Operator operateur, Valeur vDroite) {
			return new ConditionValeur(getVariable(idGauche), operateur, vDroite);
		}
	}

	/**
	 * Traiteur pour les statistiques : tracke les changements de valeurs et les enregistre dans les formules retenues.
	 * 
	 * @author Bruju
	 *
	 */
	private class TraiteurEnregistreur extends Traiteur {
		/**
		 * Crée un traiteur enregistreur pour la statistique
		 * 
		 * @param stat La statistique
		 */
		public TraiteurEnregistreur() {
		}

		@Override
		public void changeVariable(Integer variable, Operator operator, Valeur vDroite) {
			if (affecterLesAffichage) {
				super.changeVariable(variable, operator, vDroite);
			}
		}
	}
}
