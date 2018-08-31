package fr.bruju.rmeventreader.implementationexec.chercheurdevariables;

import java.util.HashMap;
import java.util.HashSet;

import fr.bruju.rmeventreader.actionmakers.executeur.controlleur.ExecuteurInstructions;
import fr.bruju.rmeventreader.actionmakers.executeur.modele.calcul.OpMathematique;
import fr.bruju.rmeventreader.actionmakers.executeur.modele.interfaces.FixeVariable;
import fr.bruju.rmeventreader.actionmakers.executeur.modele.interfaces.ValeurDroiteVariable;
import fr.bruju.rmeventreader.actionmakers.executeur.modele.interfaces.ValeurGauche;
import fr.bruju.rmeventreader.actionmakers.executeur.modele.interfaces.ValeurMembre;
import fr.bruju.rmeventreader.actionmakers.executeur.modele.objets.ArrierePlanCombat;
import fr.bruju.rmeventreader.actionmakers.executeur.modele.objets.Condition;
import fr.bruju.rmeventreader.actionmakers.executeur.modele.objets.Condition.CondVariable;
import fr.bruju.rmeventreader.actionmakers.executeur.modele.objets.Couleur;
import fr.bruju.rmeventreader.actionmakers.executeur.modele.objets.EvenementDeplacable;
import fr.bruju.rmeventreader.actionmakers.executeur.modele.objets.ExecEnum.CombatComportementFuite;
import fr.bruju.rmeventreader.actionmakers.executeur.modele.objets.ExecEnum.ConditionsDeCombat;
import fr.bruju.rmeventreader.actionmakers.executeur.modele.objets.ExecEnum.TypeEffet;
import fr.bruju.rmeventreader.actionmakers.executeur.modele.objets.ExecEnum.Vehicule;

import fr.bruju.rmeventreader.actionmakers.executeur.modele.objets.Pointeur;
import fr.bruju.rmeventreader.actionmakers.executeur.modele.objets.ValeurFixe;
import fr.bruju.rmeventreader.actionmakers.executeur.modele.objets.Variable;
import fr.bruju.rmeventreader.actionmakers.executeur.modele.objets.VariableHeros.Caracteristique;
import fr.bruju.rmeventreader.actionmakers.executeur.modele.objets.VariablePlage;
import fr.bruju.rmeventreader.actionmakers.executeur.modele.visiteur.VisiteurFixeVariable;
import fr.bruju.rmeventreader.actionmakers.executeur.modele.visiteur.VisiteurMembre;
import fr.bruju.rmeventreader.actionmakers.executeur.modele.visiteur.VisiteurValeurDroite;
import fr.bruju.rmeventreader.actionmakers.executeur.modele.visiteur.VisiteurValeurDroiteVariable;
import fr.bruju.rmeventreader.actionmakers.executeur.modele.visiteur.VisiteurValeurGauche;

/**
 * Executeur d'instructions qui ajoute des références si il trouve des instructions en lien à des variables.
 * 
 * @author Bruju
 *
 */
public class Chercheur implements ExecuteurInstructions, VisiteurValeurGauche<Void>, VisiteurValeurDroite<Void>,
		VisiteurFixeVariable<Void>, VisiteurValeurDroiteVariable<Void>, VisiteurMembre<Void>, Condition.Visiteur<Void> {
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

	@Override
	public boolean getBooleenParDefaut() {
		return true;
	}

	@Override
	public void SaisieMessages_saisieNombre(int idVariable, int nombreDeChiffres) {
		ajouterVariable(idVariable);
	}

	@Override
	public Void visit(Variable variable) {
		ajouterVariable(variable.idVariable);
		return null;
	}

	@Override
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

		visit(valeurGauche);
		visit(valeurDroite);

	}

	@Override
	public void Variables_changerVariable(ValeurGauche valeurGauche, OpMathematique operateur,
			ValeurDroiteVariable valeurDroite) {

		visit(valeurGauche);
		visit(valeurDroite);

	}

	@Override
	public void Variables_modifierArgent(boolean ajouter, FixeVariable quantite) {

		visit(quantite);

	}

	@Override
	public void Variables_modifierObjets(boolean ajouter, FixeVariable objet, FixeVariable quantite) {

		visit(objet);
		visit(quantite);

	}

	@Override
	public void Equipe_modifierEquipe(boolean ajouter, FixeVariable personnage) {

		visit(personnage);

	}

	@Override
	public void Equipe_modifierExperience(ValeurMembre cible, Caracteristique stat, boolean ajouter,
			FixeVariable quantite, boolean verbose) {

		visit(cible);
		visit(quantite);

	}

	@Override
	public void Equipe_modifierStatistique(ValeurMembre cible, Caracteristique stat, boolean ajouter,
			FixeVariable quantite) {

		visit(cible);
		visit(quantite);

	}

	@Override
	public void Equipe_modifierCompetence(ValeurMembre cible, boolean ajouter, FixeVariable sort) {

		visit(cible);
		visit(sort);

	}

	@Override
	public void Equipe_equiper(ValeurMembre cible, FixeVariable objet) {

		visit(cible);
		visit(objet);

	}

	@Override
	public void Equipe_desequiper(ValeurMembre cible) {

		visit(cible);

	}

	@Override
	public void Equipe_desequiper(ValeurMembre cible, Caracteristique type) {

		visit(cible);

	}

	@Override
	public void Equipe_modifierHP(ValeurMembre cible, boolean ajouter, FixeVariable quantite, boolean peutTuer) {

		visit(cible);
		visit(quantite);

	}

	@Override
	public void Equipe_modifierStatut(ValeurMembre cible, boolean infliger, int numeroStatut) {

		visit(cible);

	}

	@Override
	public void Equipe_soignerCompletement(ValeurMembre cible) {

		visit(cible);

	}

	@Override
	public void Combat_simulerAttaque(ValeurMembre cible, int puissance, int effetDefense, int effetIntel, int variance,
			int degatsEnregistresDansVariable) {

		visit(cible);
		this.ajouterVariable(degatsEnregistresDansVariable);

	}

	@Override
	public boolean Combat_lancerCombat(FixeVariable idCombat, ConditionsDeCombat conditions,
			ArrierePlanCombat arrierePlan, CombatComportementFuite fuite, boolean defaitePossible, boolean avantage) {

		visit(idCombat);

		return true;
	}

	@Override
	public void Jeu_deplacerVehicule(Vehicule vehicule, FixeVariable map, FixeVariable x, FixeVariable y) {

		visit(map);
		visit(x);
		visit(y);

	}

	@Override
	public void Jeu_deplacerEvenement(EvenementDeplacable deplacable, FixeVariable x, FixeVariable y) {

		visit(x);
		visit(y);

	}

	@Override
	public void Jeu_stockerIdTerrain(FixeVariable x, FixeVariable y, int variable) {

		visit(x);
		visit(y);
		this.ajouterVariable(variable);

	}

	@Override
	public void Jeu_stockerIdEvenement(FixeVariable x, FixeVariable y, int variable) {

		visit(x);
		visit(y);
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

		visit(x);
		visit(y);

	}

	@Override
	public void Flot_appelEvenementCarte(FixeVariable evenement, FixeVariable page) {

		visit(evenement);
		visit(page);

	}

	@Override
	public boolean Flot_si(Condition condition) {
		visit(condition);
		return true;
	}

	@Override
	public Void visit(ValeurFixe valeur) {
		return null;
	}

	@Override
	public Void visit(Pointeur pointeur) {
		return null;
	}

	@Override
	public Void visit(CondVariable condition) {
		ajouterVariable(condition.variable);

		visit(condition.valeurDroite);

		return null;
	}

}
