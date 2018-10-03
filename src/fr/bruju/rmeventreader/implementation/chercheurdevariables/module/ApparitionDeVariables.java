package fr.bruju.rmeventreader.implementation.chercheurdevariables.module;

import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import fr.bruju.rmeventreader.actionmakers.controlleur.ExecuteurInstructions;
import fr.bruju.rmeventreader.actionmakers.controlleur.ExecuteurInstructionsEtoile;
import fr.bruju.rmeventreader.actionmakers.modele.ArrierePlanCombat;
import fr.bruju.rmeventreader.actionmakers.modele.Condition;
import fr.bruju.rmeventreader.actionmakers.modele.Couleur;
import fr.bruju.rmeventreader.actionmakers.modele.EvenementDeplacable;
import fr.bruju.rmeventreader.actionmakers.modele.FixeVariable;
import fr.bruju.rmeventreader.actionmakers.modele.OpMathematique;
import fr.bruju.rmeventreader.actionmakers.modele.ValeurDroiteVariable;
import fr.bruju.rmeventreader.actionmakers.modele.ValeurGauche;
import fr.bruju.rmeventreader.actionmakers.modele.ValeurMembre;
import fr.bruju.rmeventreader.actionmakers.modele.Variable;
import fr.bruju.rmeventreader.actionmakers.modele.Condition.CondVariable;
import fr.bruju.rmeventreader.actionmakers.modele.ExecEnum.CombatComportementFuite;
import fr.bruju.rmeventreader.actionmakers.modele.ExecEnum.ConditionsDeCombat;
import fr.bruju.rmeventreader.actionmakers.modele.ExecEnum.TypeEffet;
import fr.bruju.rmeventreader.actionmakers.modele.ExecEnum.Vehicule;
import fr.bruju.rmeventreader.actionmakers.modele.VariableHeros.Caracteristique;
import fr.bruju.rmeventreader.actionmakers.reference.Reference;
import fr.bruju.rmeventreader.implementation.chercheurdevariables.BaseDeRecherche;

/**
 * Base de recherches des variables utilisées
 * @author Bruju
 *
 */
public class ApparitionDeVariables implements BaseDeRecherche {
	/** Liste des références pour chaque variable */
	private Map<Integer, Set<Reference>> variablesCherchees = new TreeMap<>();

	/**
	 * Crée une base de recherche de l'apprition des variables données
	 * @param is La liste des variables
	 */
	public ApparitionDeVariables(int[] is) {
		for (int id : is) {
			variablesCherchees.put(id, new TreeSet<>());
		}
	}

	@Override
	public void afficher() {
		variablesCherchees.forEach(ApparitionDeVariables::afficher);
	}

	/**
	 * Affiche la liste des références pour la varible
	 * @param variable La variable
	 * @param references sa liste de références
	 */
	private static void afficher(Integer variable, Set<Reference> references) {
		System.out.println("==" + variable + "==");
		
		references.forEach(reference -> System.out.println(reference.getString()));
		
		System.out.println();
	}

	@Override
	public ExecuteurInstructions getExecuteur(Reference ref) {
		return new Chercheur(ref, variablesCherchees);
	}
	
	/**
	 * Executeur d'instructions qui ajoute des références si il trouve des instructions en lien à des variables.
	 * 
	 * @author Bruju
	 *
	 */
	public class Chercheur implements ExecuteurInstructionsEtoile {
		/** Référence à ajouter */
		private Reference reference;
		/** Map d'association variables - références à compléter */
		private Map<Integer, Set<Reference>> variablesCherchees;

		/**
		 * Crée un nouveau chercheur de références à une variable
		 * 
		 * @param reference La référence
		 * @param variablesCherchees Map à compléter
		 */
		public Chercheur(Reference reference, Map<Integer, Set<Reference>> variablesCherchees) {
			this.reference = reference;
			this.variablesCherchees = variablesCherchees;
		}

		
		/** Note l'occurence de la variable donnée pour cette référence */
		public void ajouterVariable(int numero) {
			Set<Reference> set = variablesCherchees.get(numero);
			if (set == null)
				return;

			set.add(reference);
		}

		
		/** Note l'occurence de la variable donnée pour cette référence */
		public Void ajouterVariable(Variable variable) {
			ajouterVariable(variable.idVariable);
			return null;
		}
		
		@Override
		public boolean getBooleenParDefaut() {
			return true;
		}

		@Override
		public void SaisieMessages_saisieNombre(int idVariable, int nombreDeChiffres) {
			ajouterVariable(idVariable);
		}
		
		@Override
		public void Variables_affecterVariable(ValeurGauche valeurGauche, ValeurDroiteVariable valeurDroite) {
			valeurGauche.appliquerG(this::ajouterVariable, null, null);
			valeurDroite.appliquerDroite(null, this::ajouterVariable, null);
		}

		@Override
		public void Variables_changerVariable(ValeurGauche valeurGauche, OpMathematique operateur,
				ValeurDroiteVariable valeurDroite) {
			valeurGauche.appliquerG(this::ajouterVariable, null, null);
			valeurDroite.appliquerDroite(null, this::ajouterVariable, null);
		}

		@Override
		public void Variables_modifierArgent(boolean ajouter, FixeVariable quantite) {
			quantite.appliquerFV(null, this::ajouterVariable);
		}

		@Override
		public void Variables_modifierObjets(boolean ajouter, FixeVariable objet, FixeVariable quantite) {
			objet.appliquerFV(null, this::ajouterVariable);
			quantite.appliquerFV(null, this::ajouterVariable);
		}

		@Override
		public void Equipe_modifierEquipe(boolean ajouter, FixeVariable personnage) {
			personnage.appliquerFV(null, this::ajouterVariable);
		}

		@Override
		public void Equipe_modifierExperience(ValeurMembre cible, Caracteristique stat, boolean ajouter,
				FixeVariable quantite, boolean verbose) {
			cible.appliquerMembre(null, null, this::ajouterVariable);
			quantite.appliquerFV(null, this::ajouterVariable);
		}

		@Override
		public void Equipe_modifierStatistique(ValeurMembre cible, Caracteristique stat, boolean ajouter,
				FixeVariable quantite) {
			cible.appliquerMembre(null, null, this::ajouterVariable);
			quantite.appliquerFV(null, this::ajouterVariable);
		}

		@Override
		public void Equipe_modifierCompetence(ValeurMembre cible, boolean ajouter, FixeVariable sort) {
			cible.appliquerMembre(null, null, this::ajouterVariable);
			sort.appliquerFV(null, this::ajouterVariable);
		}

		@Override
		public void Equipe_equiper(ValeurMembre cible, FixeVariable objet) {
			cible.appliquerMembre(null, null, this::ajouterVariable);
			objet.appliquerFV(null, this::ajouterVariable);
		}

		@Override
		public void Equipe_desequiper(ValeurMembre cible) {
			cible.appliquerMembre(null, null, this::ajouterVariable);
		}

		@Override
		public void Equipe_desequiper(ValeurMembre cible, Caracteristique type) {
			cible.appliquerMembre(null, null, this::ajouterVariable);
		}

		@Override
		public void Equipe_modifierHP(ValeurMembre cible, boolean ajouter, FixeVariable quantite, boolean peutTuer) {
			cible.appliquerMembre(null, null, this::ajouterVariable);
			quantite.appliquerFV(null, this::ajouterVariable);
		}

		@Override
		public void Equipe_modifierStatut(ValeurMembre cible, boolean infliger, int numeroStatut) {
			cible.appliquerMembre(null, null, this::ajouterVariable);
		}

		@Override
		public void Equipe_soignerCompletement(ValeurMembre cible) {
			cible.appliquerMembre(null, null, this::ajouterVariable);
		}

		@Override
		public void Combat_simulerAttaque(ValeurMembre cible, int puissance, int effetDefense, int effetIntel, int variance,
				int degatsEnregistresDansVariable) {
			cible.appliquerMembre(null, null, this::ajouterVariable);
			ajouterVariable(degatsEnregistresDansVariable);
		}

		@Override
		public boolean Combat_lancerCombat(FixeVariable idCombat, ConditionsDeCombat conditions,
				ArrierePlanCombat arrierePlan, CombatComportementFuite fuite, boolean defaitePossible, boolean avantage) {
			idCombat.appliquerFV(null, this::ajouterVariable);
			return true;
		}

		@Override
		public void Jeu_deplacerVehicule(Vehicule vehicule, FixeVariable map, FixeVariable x, FixeVariable y) {
			map.appliquerFV(null, this::ajouterVariable);
			x.appliquerFV(null, this::ajouterVariable);
			y.appliquerFV(null, this::ajouterVariable);
		}

		@Override
		public void Jeu_deplacerEvenement(EvenementDeplacable deplacable, FixeVariable x, FixeVariable y) {
			x.appliquerFV(null, this::ajouterVariable);
			y.appliquerFV(null, this::ajouterVariable);
		}

		@Override
		public void Jeu_stockerIdTerrain(FixeVariable x, FixeVariable y, int variable) {
			x.appliquerFV(null, this::ajouterVariable);
			y.appliquerFV(null, this::ajouterVariable);
			this.ajouterVariable(variable);
		}

		@Override
		public void Jeu_stockerIdEvenement(FixeVariable x, FixeVariable y, int variable) {
			x.appliquerFV(null, this::ajouterVariable);
			y.appliquerFV(null, this::ajouterVariable);
			this.ajouterVariable(variable);
		}

		@Override
		public void Image_afficher(int numeroImage, String nomImage, FixeVariable xImage, FixeVariable yImage,
				int transparenceHaute, int transparenceBasse, int agrandissement, Couleur couleur, int saturation,
				TypeEffet typeEffet, int intensiteEffet, boolean transparence, boolean defilementAvecCarte) {
			if (numeroImage >= 10000 && numeroImage <= 50000) {
				this.ajouterVariable(numeroImage - 10000);
			}
			if (numeroImage >= 50000) {
				this.ajouterVariable(numeroImage - 50000);
				this.ajouterVariable(numeroImage - 50000 + 1);
			}
		}

		@Override
		public void Image_deplacer(int numeroImage, FixeVariable xImage, FixeVariable yImage, int transparenceHaute,
				int transparenceBasse, int agrandissement, Couleur couleur, int saturation, TypeEffet typeEffet,
				int intensiteEffet, int temps, boolean pause) {
			if (numeroImage >= 10000 && numeroImage <= 50000) {
				this.ajouterVariable(numeroImage - 10000);
			}
			if (numeroImage >= 50000) {
				this.ajouterVariable(numeroImage - 50000);
				this.ajouterVariable(numeroImage - 50000 + 1);
			}
		}

		@Override
		public void Media_jouerFilm(String nomFilm, FixeVariable x, FixeVariable y, int longueur, int largeur) {
			x.appliquerFV(null, this::ajouterVariable);
			y.appliquerFV(null, this::ajouterVariable);
		}

		@Override
		public void Flot_appelEvenementCarte(FixeVariable evenement, FixeVariable page) {
			evenement.appliquerFV(null, this::ajouterVariable);
			page.appliquerFV(null, this::ajouterVariable);
		}

		@Override
		public boolean Flot_si(Condition condition) {
			condition.appliquerVariable(this::visit);
			return true;
		}


		public Void visit(CondVariable condition) {
			ajouterVariable(condition.variable);
			condition.valeurDroite.appliquerFV(null, this::ajouterVariable);
			return null;
		}
	}
}
