package fr.bruju.rmeventreader.actionmakers.controlleur;

import fr.bruju.rmeventreader.actionmakers.modele.Couleur;
import fr.bruju.rmeventreader.actionmakers.modele.Deplacement;
import fr.bruju.rmeventreader.actionmakers.modele.EvenementDeplacable;
import fr.bruju.rmeventreader.actionmakers.modele.ExecEnum;
import fr.bruju.rmeventreader.actionmakers.modele.FixeVariable;
import fr.bruju.rmeventreader.actionmakers.modele.OpMathematique;
import fr.bruju.rmeventreader.actionmakers.modele.ValeurDroiteVariable;
import fr.bruju.rmeventreader.actionmakers.modele.ValeurGauche;
import fr.bruju.rmeventreader.actionmakers.modele.ValeurMembre;
import fr.bruju.rmeventreader.actionmakers.modele.VariableHeros;
import fr.bruju.rmeventreader.actionmakers.modele.ExecEnum.Direction;
import fr.bruju.rmeventreader.actionmakers.modele.ExecEnum.Intensite;
import fr.bruju.rmeventreader.actionmakers.modele.ExecEnum.Meteo;
import fr.bruju.rmeventreader.actionmakers.modele.ExecEnum.Transition;
import fr.bruju.rmeventreader.actionmakers.modele.VariableHeros.Caracteristique;

/**
 * Exécuteur d'instructions
 * <p>
 * Pour toutes les instructions, des implémentations par défaut ne faisant rien sont fournies.
 * <p>
 * Ainsi pour implémenter un exécuteur, il suffit d'implémenter les instructions pertinentes.
 * @author Bruju
 *
 */
public interface ExecuteurInstructions {
	/** Donne le module gérant les instructions liées aux messages */
	public default ModuleExecMessages getExecMessages() {
		return ModuleExecMessages.NullFalse;
	}

	/** Donne le module gérant les instructions liées aux images, vidéos et musiques */
	public default ModuleExecMedia getExecMedia() {
		return ModuleExecMedia.Null;
	}

	/** Donne le module gérant les instructions liées à la gestion du flot d'instructions */
	public default ModuleExecFlot getExecFlot() {
		return ModuleExecFlot.NullFalse;
	}

	/** Donne le module gérant les instructions liées aux systèmes */
	public default ModuleExecSysteme getExecSysteme() {
		return ModuleExecSysteme.Null;
	}

	/** Donne le module gérant les instructions liés aux systèmes intégrés de RPG Maker */
	public default ModuleExecIntegre getExecIntegre() {
		return ModuleExecIntegre.NullFalse;
	}
	
	/*
	 * VARIABLES
	 */
	
	public default void Variables_changerSwitch(ValeurGauche valeurGauche, Boolean nouvelleValeur) {
	}

	public default void Variables_affecterVariable(ValeurGauche valeurGauche, ValeurDroiteVariable valeurDroite) {
	}

	public default void Variables_changerVariable(ValeurGauche valeurGauche, OpMathematique operateur,
			ValeurDroiteVariable valeurDroite) {
	}
	
	public default void Variables_modifierArgent(boolean ajouter, FixeVariable quantite) {
	}

	public default void Variables_modifierObjets(boolean ajouter, FixeVariable objet, FixeVariable quantite) {
	}
	
	/*
	 * EQUIPE
	 */
	
	public default void Equipe_modifierEquipe(boolean ajouter, FixeVariable personnage) {
	}

	public default void Equipe_modifierExperience(ValeurMembre cible, VariableHeros.Caracteristique stat,
			boolean ajouter, FixeVariable quantite, boolean verbose) {
	}

	/**
	 * Recoit les modifications de statistiques (sauf celles de HPActuel, Niveau et Expérience)
	 * @param cible La cible
	 * @param stat La statistique affectée
	 * @param ajouter Vrai si la statistique est augmentée, faux si elle est diminuée 
	 * @param quantite La valeur à ajouter ou soustraire
	 */
	public default void Equipe_modifierStatistique(ValeurMembre cible, VariableHeros.Caracteristique stat,
			boolean ajouter, FixeVariable quantite) {
	}

	public default void Equipe_modifierCompetence(ValeurMembre cible, boolean ajouter, FixeVariable sort) {
	}

	public default void Equipe_equiper(ValeurMembre cible, FixeVariable objet) {
	}

	/**
	 * Désequipe tous les objets de la cible
	 * @param cible La cible
	 */
	public default void Equipe_desequiper(ValeurMembre cible) {
		
	}

	/**
	 * Désequipe un équipement de la cible
	 * @param cible La cible
	 * @param type Le type d'objet à retirer. Seules les valeurs ARME, CASQUE, ARMURE, ACCESSOIRE et BOUCLIER peuvent
	 * être appelées.
	 */
	public default void Equipe_desequiper(ValeurMembre cible, Caracteristique type) {
		
	}

	public default void Equipe_modifierHP(ValeurMembre cible, boolean ajouter, FixeVariable quantite,
			boolean peutTuer) {
	}

	public default void Equipe_modifierStatut(ValeurMembre cible, boolean infliger, int numeroStatut) {
	}

	public default void Equipe_soignerCompletement(ValeurMembre cible) {
	}
	
	/*
	 * Jeu
	 */
	
	public default void Jeu_afficherAnimation(EvenementDeplacable deplacable, int numeroAnimation, boolean pause,
			boolean pleinEcran) {
		
	}
	
	
	public default void Jeu_afficherEcran(Transition transition) {
	}

	public default void Jeu_ajouterTeleporteur(int map, int x, int y, int switchActive) {
		
	}

	public default void Jeu_attendre(int TempsDixiemeDeSec) {
	}
	
	public default void Jeu_attendreAppuiTouche() {}

	public default void Jeu_cacherEcran(Transition transition) {
	}
	
	/**
	 * 
	 * @param nomPanorama
	 * @param defilementHorizontal -1 si pas de défilement, sinon la vitesse de l'auto défilement
	 * @param defilementVertical
	 */
	public default void Jeu_changerPanorama(String nomPanorama, int defilementHorizontal, int defilementVertical) {
		
	}
	
	public default void Jeu_defilementBloquer() {}

	public default void Jeu_defilementPonctuel(int vitesse, boolean pause, Direction direction, int nombreDeCases) {}

	public default void Jeu_defilementReprendre() {}

	public default void Jeu_defilementRetour(int vitesse, boolean pause) {}

	public default void Jeu_definirPointDeFuite(int map, int x, int y, int switchActive) {
		
	}

	/**
	 * 
	 * @param deplacable
	 * @param vitesse
	 * @param repeter
	 * @param ignorerBloquage
	 * @param deplacement Non implémenté
	 */
	public default void Jeu_deplacer(EvenementDeplacable deplacable, int vitesse, boolean repeter, boolean ignorerBloquage,
			Deplacement deplacement) {
		
	}

	public default void Jeu_deplacerEvenement(EvenementDeplacable deplacable, FixeVariable x, FixeVariable y) {
	}

	public default void Jeu_deplacerVehicule(ExecEnum.Vehicule vehicule, FixeVariable map, FixeVariable x,
			FixeVariable y) {
	}

	public default void Jeu_ecranTitre() {
		
	}

	public default void Jeu_entrerVehicule() {
	}

	public default void Jeu_finDuJeu() {
		
	}

	public default void Jeu_flasherEvenement(EvenementDeplacable deplacable, Couleur couleur, int intensite, boolean pause,
			int tempsDixiemeDeSec) {
		
	}

	public default void Jeu_flashLancer(Couleur couleur, int intensite, int tempsMs, boolean pause, boolean flashUnique) {
		
	}

	public default void Jeu_flashStop() {
		
	}

	public default void Jeu_inverserEvenements(EvenementDeplacable deplacable, EvenementDeplacable deplacable2) {
	}

	public default void Jeu_memoriserPosition(int idMap, int x, int y) {
	}

	public default void Jeu_modifierCarreau(boolean coucheHaute, int remplace, int remplacant) {
		
	}

	public default void Jeu_modifierChipset(int numero) {}

	public default void Jeu_modifierFrequenceRencontres(int taux) {
		
	}

	public default void Jeu_modifierMeteo(Meteo meteo, Intensite intensite) {}

	public default void Jeu_ouvrirMenu() {
		
	}

	public default void Jeu_ouvrirMenuSauvegarde() {
		
	}

	public default void Jeu_retirerTeleporteur(int map, int x, int y) {
		
	}

	public default void Jeu_revenirPosition(int variableMap, int variableX, int variableY) {
	}

	public default void Jeu_stockerIdEvenement(FixeVariable x, FixeVariable y, int variable) {
	}

	public default void Jeu_stockerIdTerrain(FixeVariable x, FixeVariable y, int variable) {
	}

	public default void Jeu_teleporter(int idMap, int x, int y, ExecEnum.Direction direction) {
	}

	
	public default void Jeu_tonEcran(Couleur couleur, int saturation, int tempsMs, boolean pause) {
	}

	public default void Jeu_toutDeplacer() {}

	public default void Jeu_toutStopper() {}

	public default void Jeu_transparenceHeros(boolean estTransparent) {
		
	}

	public default void Jeu_tremblementCommencer(int force, int intensite) {
		
	}

	public default void Jeu_tremblementPonctuel(int force, int intensite, int temps, boolean bloquant) {
	}

	public default void Jeu_tremblementStop() {
	}
	

	public default void Chrono_arreter(int numeroChrono) {
	}
	
	public default void Chrono_lancer(int numeroChrono, boolean afficherChrono, boolean continuerPendantCombat) {
	}
	
	public default void Chrono_modifier(int numeroChrono, FixeVariable dechiffrerFixeVariable) {
	}
}
