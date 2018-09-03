package fr.bruju.rmeventreader.actionmakers.executeur.controlleur;

import fr.bruju.rmeventreader.actionmakers.executeur.modele.objets.FixeVariable;
import fr.bruju.rmeventreader.actionmakers.executeur.modele.objets.ValeurMembre;
import fr.bruju.rmeventreader.actionmakers.executeur.modele.objets.VariableHeros;
import fr.bruju.rmeventreader.actionmakers.executeur.modele.objets.VariableHeros.Caracteristique;

public interface ModuleExecEquipe {
	public static final ModuleExecEquipe Null = new ModuleExecEquipe() {};
	

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
}
