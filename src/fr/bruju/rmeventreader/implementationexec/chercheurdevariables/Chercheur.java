package fr.bruju.rmeventreader.implementationexec.chercheurdevariables;

import java.util.HashMap;
import java.util.HashSet;

import fr.bruju.rmeventreader.actionmakers.executeur.controlleur.ExecuteurInstructions;
import fr.bruju.rmeventreader.actionmakers.executeur.modele.objets.ArrierePlanCombat;
import fr.bruju.rmeventreader.actionmakers.executeur.modele.objets.Condition;
import fr.bruju.rmeventreader.actionmakers.executeur.modele.objets.Condition.CondVariable;
import fr.bruju.rmeventreader.actionmakers.executeur.modele.objets.Couleur;
import fr.bruju.rmeventreader.actionmakers.executeur.modele.objets.EvenementDeplacable;
import fr.bruju.rmeventreader.actionmakers.executeur.modele.objets.FixeVariable;
import fr.bruju.rmeventreader.actionmakers.executeur.modele.objets.OpMathematique;
import fr.bruju.rmeventreader.actionmakers.executeur.modele.objets.ExecEnum.CombatComportementFuite;
import fr.bruju.rmeventreader.actionmakers.executeur.modele.objets.ExecEnum.ConditionsDeCombat;
import fr.bruju.rmeventreader.actionmakers.executeur.modele.objets.ExecEnum.TypeEffet;
import fr.bruju.rmeventreader.actionmakers.executeur.modele.objets.ExecEnum.Vehicule;

import fr.bruju.rmeventreader.actionmakers.executeur.modele.objets.ValeurDroiteVariable;
import fr.bruju.rmeventreader.actionmakers.executeur.modele.objets.ValeurGauche;
import fr.bruju.rmeventreader.actionmakers.executeur.modele.objets.ValeurMembre;
import fr.bruju.rmeventreader.actionmakers.executeur.modele.objets.Variable;
import fr.bruju.rmeventreader.actionmakers.executeur.modele.objets.VariableHeros.Caracteristique;
import fr.bruju.rmeventreader.actionmakers.executeur.modele.objets.VariablePlage;

/**
 * Executeur d'instructions qui ajoute des références si il trouve des instructions en lien à des variables.
 * 
 * @author Bruju
 *
 */
public class Chercheur implements ExecuteurInstructions {
	/** Référence à ajouter */
	private Reference reference;
	/** Map d'association variables - références à compléter */
	private HashMap<Integer, HashSet<Reference>> variablesCherchees;

	/**
	 * Crée un nouveau chercheur de références à une variable
	 * 
	 * @param reference La référence
	 * @param variablesCherchees Map à compléter
	 */
	public Chercheur(Reference reference, HashMap<Integer, HashSet<Reference>> variablesCherchees) {
		this.reference = reference;
		this.variablesCherchees = variablesCherchees;
	}

	/**
	 * Ajoute une variable dont il faut pister les référénces
	 * 
	 * @param numero Le numéro de la variable
	 */
	public void ajouterVariable(int numero) {
		HashSet<Reference> set = variablesCherchees.get(numero);
		if (set == null)
			return;

		set.add(reference);
	}

	
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

	public Void visit(Variable variable) {
		ajouterVariable(variable.idVariable);
		return null;
	}

	public Void visit(VariablePlage plage) {
		int min = Math.min(plage.idVariableMin, plage.idVariableMax);
		int max = Math.max(plage.idVariableMin, plage.idVariableMax);

		for (int i = min; i <= max; i++) {
			ajouterVariable(i);
		}

		return null;
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
