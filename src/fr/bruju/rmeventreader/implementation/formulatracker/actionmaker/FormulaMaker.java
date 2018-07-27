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
import fr.bruju.rmeventreader.implementation.formulatracker.composant.bouton.BStatistique;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.bouton.Bouton;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.condition.Condition;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.valeur.VStatistique;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.valeur.Valeur;
import fr.bruju.rmeventreader.implementation.formulatracker.formule.attaques.FormuleDeDegats;
import fr.bruju.rmeventreader.implementation.formulatracker.formule.attaques.ModifStat;
import fr.bruju.rmeventreader.implementation.formulatracker.formule.personnage.Personnages;
import fr.bruju.rmeventreader.implementation.formulatracker.formule.personnage.Statistique;
import fr.bruju.rmeventreader.utilitaire.Pair;
import fr.bruju.rmeventreader.utilitaire.Utilitaire;

/**
 * Constructeur de formules à partir d'un fichier pour donner le contenu des variables trackées en fonction d'autres
 * variables.
 * 
 * @author Bruju
 *
 */
public class FormulaMaker implements ActionMakerDefalse {
	/** Un objet permettant de convertir des valeurs ActionMaker en valeurs FormulaTracker */
	private Traducteur traducteur;
	/** Un objet appelé lorsqu'une ligne quelconque est lue */
	private Traiteur traiteurParDefaut;
	/** Association entre numéro de variables et traiteurs à appeler */
	private Map<Integer, TraiteurEnregistreur> traiteursSpeciaux;
	/** Etat mémoire actuel */
	private EtatMemoire etat;
	/**
	 * Si vrai, les états mémoires sont modifiés lorsqu'on appelle un traiteur spécial Mettre à faux peut simplifier les
	 * formules construites, au détriement de l'exactitude
	 */
	private boolean affecterLesAffichage;

	/**
	 * Construit un constructeur de formules à partir d'une base de personnages
	 * 
	 * @param contexte La base de personnages
	 * @param affecterLesAffichage Si faux, les formules seront plus simples mais moins exactes
	 */
	public FormulaMaker(Personnages contexte, boolean affecterLesAffichage) {
		this.affecterLesAffichage = affecterLesAffichage;
		Map<Integer, Valeur> variablesExistantes = new HashMap<>();
		Map<Integer, Bouton> interrupteursExistants = new HashMap<>();

		fixerPersonnages(contexte, variablesExistantes, interrupteursExistants);

		etat = new EtatMemoire(variablesExistantes, interrupteursExistants);

		traducteur = new Traducteur();
		traiteurParDefaut = new Traiteur();
	}

	/**
	 * Met en place les traitements et des éléments de bases pour les variables correspondant à des statistiques. On
	 * trackera ensuite les modifications de ces statistiques
	 * 
	 * @param contexte La base de personnages
	 * @param variablesExistantes Une carte vide associant variables et valeurs, qui sera remplie ici
	 * @param interrupteursExistants Une carte vide associant interrupteurs et valeurs, qui sera remplie ici
	 */
	private void fixerPersonnages(Personnages contexte, Map<Integer, Valeur> variablesExistantes,
			Map<Integer, Bouton> interrupteursExistants) {
		Map<Integer, TraiteurEnregistreur> traiteursSpeciaux = new HashMap<>();

		contexte.getPersonnages().stream().flatMap(p -> p.getStatistiques().values().stream()).forEach(stat -> {
			variablesExistantes.put(stat.position, new VStatistique(stat));
			traiteursSpeciaux.put(stat.position, new TraiteurEnregistreur(stat));
		});
		
		contexte.getPersonnages().stream().flatMap(p -> p.getProprietes().values().stream()).forEach(stat -> 
			interrupteursExistants.put(stat.position, new BStatistique(stat)));

		this.traiteursSpeciaux = traiteursSpeciaux;
	}

	// Entrées / Sorties

	/**
	 * Donne un objet résultat contenant les résultats de l'exécution du FormulaMaker
	 */
	public Map<ModifStat, List<FormuleDeDegats>> getResultat() {
		Map<ModifStat, List<FormuleDeDegats>> resultat = new HashMap<>();
		traiteursSpeciaux.values().forEach(traiteur -> traiteur.remplir(resultat));
		return resultat;
	}

	/**
	 * Donne le traiteur à utiliser pour le numéro de variable donné
	 * 
	 * @param numVariable Le numéro de variable
	 * @return Le traiteur à utiliser pour cette variable
	 */
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
	public Bouton getInterrupteur(int numero) {
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
		public void condOnSwitch(Bouton interrupteur, boolean valeur) {
			Condition condition = traducteur.getConditionSwitch(interrupteur, valeur);
			etat = etat.creerFils(condition);
		}

		/** Condition sur un objet équipé */
		public void condOnEquippedItem(int heros, int objet) {
			Condition condition = traducteur.getConditionObjetEquipe(heros, objet);
			etat = etat.creerFils(condition);
		}

		/** Condition sur une variable */
		public void condOnVariable(Valeur vGauche, Operator operateur, Valeur vDroite) {
			Condition condition = traducteur.getConditionVariable(vGauche, operateur, vDroite);
			etat = etat.creerFils(condition);
		}

		/** Changement de branche d'état mémoire */
		public void condElse() {
			etat = etat.getPetitFrere();
		}

		/** Retour à l'état mémoire père */
		public void condEnd() {
			etat = etat.revenirAuPere();
		}
	}

	/**
	 * Traiteur pour les statistiques : tracke les changements de valeurs et les enregistre dans les formules retenues.
	 * 
	 * @author Bruju
	 *
	 */
	private class TraiteurEnregistreur extends Traiteur {
		/** Statistique concernée */
		private Statistique stat;

		/** Liste des formules enregistrées */
		private List<Pair<Operator, FormuleDeDegats>> formules;

		/**
		 * Crée un traiteur enregistreur pour la statistique
		 * 
		 * @param stat La statistique
		 */
		public TraiteurEnregistreur(Statistique stat) {
			this.stat = stat;
			formules = new ArrayList<>();
		}

		@Override
		public void changeVariable(Integer variable, Operator operator, Valeur vDroite) {
			formules.add(new Pair<>(operator, 
					new FormuleDeDegats(etat.construireListeDeConditions(), vDroite)));
			
			if (affecterLesAffichage) {
				super.changeVariable(variable, operator, vDroite);
			}
		}
		
		/**
		 * Rempli la map de resultats avec les formules trouvées par ce traiteur
		 * @param resultat La carte de résultats
		 */
		public void remplir(Map<ModifStat, List<FormuleDeDegats>> resultat) {
			formules.forEach(paire ->
				Utilitaire.mapAjouterElementAListe(resultat, new ModifStat(stat, paire.getLeft()), paire.getRight())
							);
		}
	}
}
