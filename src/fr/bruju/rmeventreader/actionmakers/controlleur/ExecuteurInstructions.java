package fr.bruju.rmeventreader.actionmakers.controlleur;

import fr.bruju.rmeventreader.actionmakers.modele.ArrierePlanCombat;
import fr.bruju.rmeventreader.actionmakers.modele.Condition;
import fr.bruju.rmeventreader.actionmakers.modele.Couleur;
import fr.bruju.rmeventreader.actionmakers.modele.Deplacement;
import fr.bruju.rmeventreader.actionmakers.modele.EvenementDeplacable;
import fr.bruju.rmeventreader.actionmakers.modele.ExecEnum;
import fr.bruju.rmeventreader.actionmakers.modele.FixeVariable;
import fr.bruju.rmeventreader.actionmakers.modele.OpMathematique;
import fr.bruju.rmeventreader.actionmakers.modele.SonParam;
import fr.bruju.rmeventreader.actionmakers.modele.ValeurDroiteVariable;
import fr.bruju.rmeventreader.actionmakers.modele.ValeurGauche;
import fr.bruju.rmeventreader.actionmakers.modele.ValeurMembre;
import fr.bruju.rmeventreader.actionmakers.modele.VariableHeros;
import fr.bruju.rmeventreader.actionmakers.modele.ExecEnum.ChoixQCM;
import fr.bruju.rmeventreader.actionmakers.modele.ExecEnum.ClasseCarac;
import fr.bruju.rmeventreader.actionmakers.modele.ExecEnum.ClasseComp;
import fr.bruju.rmeventreader.actionmakers.modele.ExecEnum.CombatComportementFuite;
import fr.bruju.rmeventreader.actionmakers.modele.ExecEnum.ConditionsDeCombat;
import fr.bruju.rmeventreader.actionmakers.modele.ExecEnum.Direction;
import fr.bruju.rmeventreader.actionmakers.modele.ExecEnum.Intensite;
import fr.bruju.rmeventreader.actionmakers.modele.ExecEnum.Meteo;
import fr.bruju.rmeventreader.actionmakers.modele.ExecEnum.Position;
import fr.bruju.rmeventreader.actionmakers.modele.ExecEnum.SujetTransition;
import fr.bruju.rmeventreader.actionmakers.modele.ExecEnum.Transition;
import fr.bruju.rmeventreader.actionmakers.modele.ExecEnum.TypeEffet;
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

	public default boolean getBooleenParDefaut() {
		return false;
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
	
	
	/*
	 * SYSTEME
	 */
	
	public default void Systeme_changerClasse(int idHeros, int idClasse, boolean revenirAuNiveau1, ClasseComp competences,
			ClasseCarac caracBase, boolean montrerCompetencesApprises) {
		
	}

	/**
	 * 
	 * @param etire Vrai = étiré, Faux = Mosaique
	 * @param premierePolice Vrai = première police, Faux = seconde police
	 * @param nomApparence Nom du fichier contenant l'apparence
	 */
	public default void Systeme_modifierApparence(boolean etire, boolean premierePolice, String nomApparence) {
	}

	public default void Systeme_modifierApparenceHeros(int idHeros, String charset, int numeroCharset,
			boolean transparent) {
	}

	public default void Systeme_modifierApparenceVehicule(ExecEnum.Vehicule vehicule, String charset,
			int numeroCharset) {
	}

	public default void Systeme_modifierCommandes(int idHeros, boolean ajout, int numeroCommande) {
		
	}
	public default void Systeme_modifierEffetSonore(ExecEnum.EffetSonore son, String nom, SonParam parametres) {
	}
	
	public default void Systeme_modifierFaceset(int idHeros, String faceset, int numeroFaceset) {
	}

	public default void Systeme_modifierGrade(int idHeros, String nouveauGrade) {
	}

	public default void Systeme_modifierMusique(ExecEnum.Musique musique, String nom, int temspFondu,
			SonParam parametres) {
	}

	public default void Systeme_modifierNom(int idHeros, String nouveauNom) {
	}

	public default void Systeme_modifierTransition(SujetTransition sujetTransition, boolean entrant,
			Transition transition) {
		
	}
	public default void Systeme_peutFuir(boolean etat) {
		
	}

	public default void Systeme_peutOuvrirLeMenu(boolean etat) {
		
	}
	
	public default void Systeme_peutSauvegarder(boolean etat) {
		
	}

	public default void Systeme_peutSeTeleporter(boolean etat) {
		
	}
	
	public default void Messages_afficherMessage(String chaine) {
	}

	public default void Messages_afficherSuiteMessage(String chaine) {
	}

	public default void Messages_modifierOptions(boolean transparent, Position position,
			boolean positionnementAuto, boolean bloquant) {	
	}

	public default boolean SaisieMessages_initierQCM(ChoixQCM choixLorsDeLAnnulation) {
		return getBooleenParDefaut();
	}

	public default boolean SaisieMessages_choixQCM(String texte, ChoixQCM numero) {
		return getBooleenParDefaut();
	}


	public default void SaisieMessages_finQCM() {
	}

	public default void SaisieMessages_saisieNombre(int idVariable, int nombreDeChiffres) {
	}

	public default void SaisieMessages_SaisieNom(int idHeros, boolean lettres, boolean afficherNomParDefaut) {
	}

	public default void Messages_appuiTouche(int numeroVariable, boolean bloquant, int enregistrementTempsMis, boolean haut,
			boolean droite, boolean bas, boolean gauche, boolean entree, boolean annuler, boolean maj, boolean chiffres,
			boolean symboles) {
		
	}
	
	/*
	 * FLOT
	 */
	
	public default void Flot_appelEvenementCarte(FixeVariable evenement, FixeVariable page) {
		
	}

	public default void Flot_appelEvenementCommun(int numero) {
		
	}

	public default void Flot_boucleDebut() {
		
	}
	
	public default void Flot_boucleFin() {
		
	}

	public default void Flot_boucleSortir() {
		
	}
	
	public default void Flot_commentaire(String message) {
		
	}

	public default void Flot_effacerCetEvenement() {
		
	}

	public default void Flot_etiquette(int numero) {
		
	}

	public default void Flot_sautEtiquette(int numero) {
		
	}

	public default boolean Flot_si(Condition condition) {
		return getBooleenParDefaut();
	}
	
	public default void Flot_siFin() {
		
	}
	public default void Flot_siNon() {
	}

	public default void Flot_stopperCetEvenement() {
		
	}
	
	/*
	 * SYSTEME PRE INTEGRES
	 */
	
	public default boolean Magasin_auberge(boolean type1, int prix) {
		return getBooleenParDefaut();
	}

	public default void Magasin_aubergeFinBranche() {
	}

	public default boolean Magasin_aubergeNonRepos() {
		return getBooleenParDefaut();
	}

	public default boolean Magasin_aubergeRepos() {
		return getBooleenParDefaut();
	}

	public default boolean Magasin_magasin(int dialogue, int[] objetsAchetables, boolean ventePossible) {
		return getBooleenParDefaut();
	}

	public default boolean Magasin_magasinBrancheNonVente() {
		return getBooleenParDefaut();
	}

	public default boolean Magasin_magasinBrancheVente() {
		return getBooleenParDefaut();
	}

	public default void Magasin_magasinFinBranche() {
	}


	public default boolean Combat_brancheDefaite() {
		return getBooleenParDefaut();
	}

	public default boolean Combat_brancheFuite() {
		return getBooleenParDefaut();
	}

	public default boolean Combat_brancheVictoire() {
		return getBooleenParDefaut();
	}


	public default void Combat_finBranche() {
	}

	public default boolean Combat_lancerCombat(FixeVariable idCombat, ConditionsDeCombat conditions, ArrierePlanCombat arrierePlan,
			CombatComportementFuite fuite, boolean defaitePossible, boolean avantage) {
		return getBooleenParDefaut();
	}

	/**
	 * Simule une attaque sur la cible
	 * @param cible Cible de l'attaque
	 * @param puissance Puissance de l'attaque
	 * @param effetDefense Effet de la défense en %
	 * @param effetIntel Effet de l'intelligence en %
	 * @param variance Variance de l'attaque
	 * @param degatsEnregistresDansVariable 0 si pas d'enregistrement des dégâts infligés dans une variable, la
	 * variable sinon
	 */
	public default void Combat_simulerAttaque(ValeurMembre cible, int puissance, int effetDefense, int effetIntel,
			int variance, int degatsEnregistresDansVariable) {
	}
	
	/*
	 * MEDIA
	 */
	
	public default void Media_arreterMusique(int tempsFondu) {
		
	}
	public default void Media_jouerFilm(String nomFilm, FixeVariable x, FixeVariable y, int longueur, int largeur) {
		
	}

	public default void Media_jouerMusique(String nomMusique, int tempsFondu, SonParam parametresMusicaux) {
		
	}

	public default void Media_jouerMusiqueMemorisee() {
		
	}

	public default void Media_jouerSon(String nomSon, SonParam parametresSonore) {
		
	}

	public default void Media_memoriserMusique() {
		
	}

	public default void Image_afficher(int numeroImage, String nomImage, FixeVariable xImage, FixeVariable yImage, int transparenceHaute,
			int transparenceBasse, int agrandissement, Couleur couleur, int saturation, TypeEffet typeEffet,
			int intensiteEffet, boolean transparence, boolean defilementAvecCarte) {
		
	}
	public default void Image_deplacer(int numeroImage, FixeVariable xImage, FixeVariable yImage, int transparenceHaute,
			int transparenceBasse, int agrandissement, Couleur couleur, int saturation, TypeEffet typeEffet,
			int intensiteEffet, int temps, boolean pause) {
		
	}
	
	public default void Image_effacer(int id) {
	}
}

