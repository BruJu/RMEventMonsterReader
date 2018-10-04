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
	/**
	 * Renvoie la valeur à retourner pour les instructions sans implémentation particulière : vrai si on veut explorer
	 * toutes les branches, faux si on ne veut pas les explorer.
	 * @return La valeur par défaut de retour des instructions à retour booléen
	 */
	public default boolean getBooleenParDefaut() {
		return false;
	}
	
	/**
	 * Simule un changement de la valeur d'interrupteurs
	 * @param valeurGauche L'interrupteur, la plage d'interrupteurs ou la variable contenant le numéro de l'interrupteur
	 * modifié.
	 * @param nouvelleValeur La nouvelle valeur. Si null, l'interrupteur est inversé
	 */
	public default void Variables_changerSwitch(ValeurGauche valeurGauche, Boolean nouvelleValeur) {
	}

	/**
	 * Affecte la valeur droite aux variables à gauche
	 * @param valeurGauche Variable à affecter
	 * @param valeurDroite Valeur à attribuer
	 */
	public default void Variables_affecterVariable(ValeurGauche valeurGauche, ValeurDroiteVariable valeurDroite) {
	}

	/**
	 * Change le contenu de la valeur gauche en appliquant l'opérateur mathématique donné avec la valeur droite
	 * @param valeurGauche La ou les variables à modifier
	 * @param operateur L'opérateur mathématique utilisé (OpMathematique.AFFECTATION n'est pas possible)
	 * @param valeurDroite La valeur à utiliser comme opérande droite de l'opération
	 */
	public default void Variables_changerVariable(ValeurGauche valeurGauche, OpMathematique operateur,
			ValeurDroiteVariable valeurDroite) {
	}
	
	/**
	 * Modifie la quantite d'argent
	 * @param ajouter Si vrai, de l'argent est gagné. Si faux, de l'argent est perdu
	 * @param quantite La quantité d'argent (valeur fixe ou dans une variable)
	 */
	public default void Variables_modifierArgent(boolean ajouter, FixeVariable quantite) {
	}

	/**
	 * Modifie la quantité d'objets possédé
	 * @param ajouter Si vrai, ajoute l'objet. Si faux le retire
	 * @param objet Le numéro de l'objet
	 * @param quantite La quantité
	 */
	public default void Variables_modifierObjets(boolean ajouter, FixeVariable objet, FixeVariable quantite) {
	}
	
	/**
	 * Modifie l'équipe en ajoutant ou enlevant un personnage
	 * @param ajouter Si vrai ajoute le personnage, si faux l'enlève
	 * @param personnage Le numéro du personnage
	 */
	public default void Equipe_modifierEquipe(boolean ajouter, FixeVariable personnage) {
	}

	/**
	 * Modifie l'expérience du groupe (soit au travers de la quanité d'expérience, soit directement par niveaux)
	 * @param cible La cible de la modification
	 * @param stat VariableHeros.Caracteristique.EXPERIENCE ou VariableHeros.Caracteristique.NIVEAU
	 * @param ajouter Vrai si il faut ajouter de l'expérience, faux si il faut en retirer
	 * @param quantite La quantité
	 * @param verbeux Vrai si un message apparait en cas d'augmentation de niveau
	 */
	public default void Equipe_modifierExperience(ValeurMembre cible, VariableHeros.Caracteristique stat,
			boolean ajouter, FixeVariable quantite, boolean verbeux) {
	}

	/**
	 * Recoit les modifications de statistiques (sauf celles de HPActuel, Niveau et Expérience)
	 * @param cible La cible
	 * @param stat La statistique affectée (exclue HPActuel, Niveau et Experience)
	 * @param ajouter Vrai si la statistique est augmentée, faux si elle est diminuée 
	 * @param quantite La valeur à ajouter ou soustraire
	 */
	public default void Equipe_modifierStatistique(ValeurMembre cible, VariableHeros.Caracteristique stat,
			boolean ajouter, FixeVariable quantite) {
	}

	/**
	 * Modifie les compétences connues
	 * @param cible La cible de la modification
	 * @param ajouter Vrai si la compétence est apprise, faux si elle est oubliée
	 * @param sort Le numéro du sort
	 */
	public default void Equipe_modifierCompetence(ValeurMembre cible, boolean ajouter, FixeVariable sort) {
	}

	/**
	 * Equipe un objet au groupe
	 * @param cible La cible
	 * @param objet L'objet à équiper
	 */
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
	
	/**
	 * Modifie les points de vie actuel du groupe
	 * @param cible La cible de la modification
	 * @param ajouter Vrai si les points de vies sont redonnés, faux si ils sont retirés
	 * @param quantite La quantité
	 * @param peutTuer Si vrai, la cible peut mourir du retrait de points de vies
	 */
	public default void Equipe_modifierHP(ValeurMembre cible, boolean ajouter, FixeVariable quantite,
			boolean peutTuer) {
	}

	/**
	 * Modifie le statut du groupe
	 * @param cible La cible
	 * @param infliger Vrai si le statut est infligé, faux si il est soigné
	 * @param numeroStatut Le numéro du statut dans la base de données
	 */
	public default void Equipe_modifierStatut(ValeurMembre cible, boolean infliger, int numeroStatut) {
	}

	/**
	 * Soigne complètement le groupe
	 * @param cible La cible
	 */
	public default void Equipe_soignerCompletement(ValeurMembre cible) {
	}
	
	
	/**
	 * Affiche une animation
	 * @param deplacable La cible de l'animation
	 * @param numeroAnimation Le numéro de l'animation dans la base de donnée
	 * @param pause Si vrai, l'évènement est mis en pause le temps de l'animation
	 * @param pleinEcran Si vrai, l'animation se joue sur l'écran entier
	 */
	public default void Jeu_afficherAnimation(EvenementDeplacable deplacable, int numeroAnimation, boolean pause,
			boolean pleinEcran) {
		
	}
	
	/**
	 * Affiche l'écran de jeu au moyen de la transition donnée
	 * @param transition La transition
	 */
	public default void Jeu_afficherEcran(Transition transition) {
	}

	/**
	 * Ajoute un point de téléportation proposé lorsque le joueur utilise un sort de téléportation
	 * @param map Le numéro de la carte
	 * @param x La position en X d'arrivée
	 * @param y La position en Y d'arrivée
	 * @param switchActive Si 0, aucun interrupteur n'est activé. Sinon le numéro de l'interrupteur activé lorsque le
	 * téléporteur est emprunté
	 */
	public default void Jeu_ajouterTeleporteur(int map, int x, int y, int switchActive) {
		
	}
	
	/**
	 * Met en pause l'évènement pendant un certains temps
	 * @param TempsDixiemeDeSec Le temps en dixièmes de secondes
	 */
	public default void Jeu_attendre(int TempsDixiemeDeSec) {
	}
	
	/**
	 * Met en pause l'évènement jusqu'à l'appuie sur une touche
	 */
	public default void Jeu_attendreAppuiTouche() {
	}

	/**
	 * Cache l'écran en utilisant une transition
	 * @param transition La transition
	 */
	public default void Jeu_cacherEcran(Transition transition) {
	}
	
	/**
	 * Change le panorama
	 * @param nomPanorama Le nom du fichier contenant le panorama
	 * @param defilementHorizontal -1 si pas de défilement, sinon la vitesse de l'auto défilement horizontal
	 * @param defilementVertical -1 si pas de défilement, sinon la vitesse de l'auto défilement vertical
	 */
	public default void Jeu_changerPanorama(String nomPanorama, int defilementHorizontal, int defilementVertical) {
		
	}
	
	/**
	 * Bloque le défilement de la caméra
	 */
	public default void Jeu_defilementBloquer() {
	}

	/**
	 * Permet de faire se déplacer la caméra
	 * @param vitesse Vitesse de déplacement
	 * @param pause Si vrai, l'évènement est mis en pause pendant le temps du déplacement
	 * @param direction La direction vers laquelle va la caméra
	 * @param nombreDeCases Le nombre de cases de déplacement
	 */
	public default void Jeu_defilementPonctuel(int vitesse, boolean pause, Direction direction, int nombreDeCases) {
	}

	/**
	 * Débloque le défilement de la caméra
	 */
	public default void Jeu_defilementReprendre() {}

	/**
	 * Fait revenir la caméra sur le joueur
	 * @param vitesse La vitesse de déplacement
	 * @param pause Si vrai, l'évènement est mis en pause pendant le retour
	 */
	public default void Jeu_defilementRetour(int vitesse, boolean pause) {
	}

	/**
	 * Permet de définir un point de fuite qui sera emprunté lorsque le joueur utilise une magie de fuite
	 * @param map Le numéro de la carte de destination
	 * @param x La position en X d'arrivée
	 * @param y La position en Y d'arrivée
	 * @param interrupteurActive Si 0, aucun interrupteur n'est activé lorsque le sort de fuite est emprunté. Sinon le
	 * numéro de l'interrupteur activé
	 */
	public default void Jeu_definirPointDeFuite(int map, int x, int y, int interrupteurActive) {
		
	}

	/**
	 * Déplace l'évènement
	 * @param deplacable L'évènement à déplacer
	 * @param vitesse La vitesse
	 * @param repeter Si vrai, le déplacement se reproduira une fois terminé
	 * @param ignorerBloquage Si vrai, si une commande de déplacement ne peut être faite, l'évènement passera à l'action
	 * suivante
	 * @param deplacement Non implémenté
	 */
	public default void Jeu_deplacer(EvenementDeplacable deplacable, int vitesse, boolean repeter, boolean ignorerBloquage,
			Deplacement deplacement) {
		// TODO : déplacement
	}

	/**
	 * Modifie la position de l'évènement. On garantie que x et y sont de mêmes types
	 * @param deplacable L'évènement à déplacer (seuls les évènements appartenant à la carte + cet évènement sont
	 * autorisés)
	 * @param x La position en X
	 * @param y La position en Y
	 */
	public default void Jeu_deplacerEvenement(EvenementDeplacable deplacable, FixeVariable x, FixeVariable y) {
	}

	/**
	 * Modifie la position d'un véhicule. On garantie que map, x et y sont de même type.
	 * @param vehicule Le véhicule
	 * @param map La carte
	 * @param x La position en X
	 * @param y La position en Y
	 */
	public default void Jeu_deplacerVehicule(ExecEnum.Vehicule vehicule, FixeVariable map, FixeVariable x,
			FixeVariable y) {
	}

	/** Amène le jeu à l'écran titre */
	public default void Jeu_ecranTitre() {
	}

	/** Entre dans le véhicule */
	public default void Jeu_entrerVehicule() {
	}

	/** Provoque un Game Over */
	public default void Jeu_finDuJeu() {
		
	}

	/**
	 * Fait flasher un évènement
	 * @param deplacable L'évènement
	 * @param couleur La couleur
	 * @param intensite L'intensité
	 * @param pause Si vrai, pendant le flash l'évènement actuel est mis en pause
	 * @param tempsDixiemeDeSec Le temps du flash en dixième de secondes
	 */
	public default void Jeu_flasherEvenement(EvenementDeplacable deplacable, Couleur couleur, int intensite, boolean pause,
			int tempsDixiemeDeSec) {
		
	}

	/**
	 * Fait flasher l'écran
	 * @param couleur La couleur
	 * @param intensite L'intensité
	 * @param temps Le temps d'un flash en dixièmes de secondes
	 * @param pause Si flashUnique est vrai et pause est vrai, l'évènement attend la fin du flash. Sinon aucun effet
	 * @param flashUnique Si vrai, un seul flash est produit. Sinon il est produit en boucle jusqu'au prochain flashStop
	 */
	public default void Jeu_flashLancer(Couleur couleur, int intensite, int temps, boolean pause, boolean flashUnique) {
		
	}

	/**
	 * Arrête de faire flasher l'écran
	 */
	public default void Jeu_flashStop() {
		
	}

	/**
	 * Inverse la position de deux évènements
	 * @param deplacable Le premier évènement
	 * @param deplacable2 Le second évènement
	 */
	public default void Jeu_inverserEvenements(EvenementDeplacable deplacable, EvenementDeplacable deplacable2) {
	}

	/**
	 * Mémorise la position du joueur
	 * @param idMap Le numéro de la variable recevant le numéro de la carte
	 * @param x Le numéro de la variable recevant la coordonnée en X
	 * @param y Le numéro de la variable recevant la coordonnée en Y
	 */
	public default void Jeu_memoriserPosition(int idMap, int x, int y) {
	}

	/**
	 * Remplace un carreau par un autre
	 * @param coucheHaute Si vrai, concerne un carreau de la couche haute, sinon de la couche basse
	 * @param remplace Le numéro du carreau à remplacer
	 * @param remplacant Le numéro du carreau qui le remplace
	 */
	public default void Jeu_modifierCarreau(boolean coucheHaute, int remplace, int remplacant) {
		
	}
	
	/**
	 * Modifie le numéro du chipset utilisé
	 * @param numero Le numéro du nouveau chipset utilisé dans la base de données
	 */
	public default void Jeu_modifierChipset(int numero) {}

	/**
	 * Modifie le taux de rencontre
	 * @param taux Le nouveau taux de rencontre
	 */
	public default void Jeu_modifierFrequenceRencontres(int taux) {
		
	}

	/**
	 * Modifie la météo
	 * @param meteo Le nouvel effet météo
	 * @param intensite L'intensité du nouveau effet
	 */
	public default void Jeu_modifierMeteo(Meteo meteo, Intensite intensite) {}

	/**
	 * Ouvre le menu
	 */
	public default void Jeu_ouvrirMenu() {
		
	}

	/**
	 * Ouvre le menu de sauvegarde
	 */
	public default void Jeu_ouvrirMenuSauvegarde() {
		
	}

	/**
	 * Retire le point de téléportation vers la carte indiquée
	 * @param map La carte vers laquelle on ne peut plus se téléporter
	 */
	public default void Jeu_retirerTeleporteur(int map) {
		
	}
	
	/**
	 * Téléporte le joueur à une position inscrite dans trois variables
	 * @param variableMap Le numéro de la variable contenant la carte de destination
	 * @param variableX Le numéro de la variable contenant la position en X d'arrivée
	 * @param variableY Le numéro de la variable contenant la position en Y d'arrivée
	 */
	public default void Jeu_revenirPosition(int variableMap, int variableX, int variableY) {
	}

	/**
	 * Stocke le numéro de l'évènement à une position donnée. X et Y sont de même type
	 * @param x La position en X
	 * @param y La position en Y
	 * @param variable Le numéro de la variable où est stocké l'id de l'évènement ainsi ciblé
	 */
	public default void Jeu_stockerIdEvenement(FixeVariable x, FixeVariable y, int variable) {
	}

	/**
	 * Stocke le numéro de l'id de terrain pointé. X et Y sont de même type
	 * @param x La position en X
	 * @param y La position en Y
	 * @param variable Le numéro de la variable recevant l'id du terrain
	 */
	public default void Jeu_stockerIdTerrain(FixeVariable x, FixeVariable y, int variable) {
	}

	/**
	 * Téléporte le joueur
	 * @param idMap La carte de destination
	 * @param x La position en X de destination
	 * @param y La position en Y de destination
	 * @param direction La nouvelle direction du personnage
	 */
	public default void Jeu_teleporter(int idMap, int x, int y, ExecEnum.Direction direction) {
	}

	/**
	 * Modifie le ton des couleurs de l'écran
	 * @param couleur La couleur
	 * @param saturation La saturation
	 * @param temps Le temps du fondu
	 * @param pause Vrai si pendant le fondu l'évènement est en pause
	 */
	public default void Jeu_tonEcran(Couleur couleur, int saturation, int temps, boolean pause) {
	}

	/**
	 * Interrompt l'évènement jusqu'à que tous les autres évènements aient fini leur déplacement
	 */
	public default void Jeu_toutDeplacer() {
	}

	/**
	 * Arrête le déplacement de tous les évènements
	 */
	public default void Jeu_toutStopper() {}

	/**
	 * Modifie la transparence du héros
	 * @param estTransparent Si vrai le héros devient invisible
	 */
	public default void Jeu_transparenceHeros(boolean estTransparent) {
		
	}

	/**
	 * Commence des tremblements réptés
	 * @param force La force du tremblement
	 * @param intensite L'intensité du tremblement
	 */
	public default void Jeu_tremblementCommencer(int force, int intensite) {
		
	}

	/**
	 * Provoque un tremblement ponctuel
	 * @param force La force du tremblement
	 * @param intensite L'intensité du tremblement
	 * @param temps Le temps de tremblement en dixièmes de secondes
	 * @param bloquant Si vrai l'évènement est interrompu pendant le tremblement
	 */
	public default void Jeu_tremblementPonctuel(int force, int intensite, int temps, boolean bloquant) {
	}

	/**
	 * Arrête les tremblements répétés
	 */
	public default void Jeu_tremblementStop() {
	}
	
	/**
	 * Arrête le chrono
	 * @param numeroChrono Le numéro du chrono à arréter
	 */
	public default void Chrono_arreter(int numeroChrono) {
	}
	
	/**
	 * Démarre un chrono
	 * @param numeroChrono Le numéro du chrono
	 * @param afficherChrono Vrai si le chrono doit être affiché
	 * @param continuerPendantCombat Vrai si le chrono continue pendant les combats
	 */
	public default void Chrono_lancer(int numeroChrono, boolean afficherChrono, boolean continuerPendantCombat) {
	}
	
	/**
	 * Modifie la valuer d'un chrono
	 * @param numeroChrono Le numéro du chrono
	 * @param valeur La valeur à insérer en secondes
	 */
	public default void Chrono_modifier(int numeroChrono, FixeVariable valeur) {
	}
	
	/**
	 * Change la classe d'un personnage
	 * @param idHeros Le personnage
	 * @param idClasse La classe
	 * @param revenirAuNiveau1 Si vrai le personnage revient au niveau 1
	 * @param competences Détermination de ce qu'il faut faire des sorts du personnages
	 * @param caracBase Détermine ce qu'il faut faire des statistiques
	 * @param montrerCompetencesApprises Si vrai un message s'affichera pour les compétences acquises
	 */
	public default void Systeme_changerClasse(int idHeros, int idClasse, boolean revenirAuNiveau1, ClasseComp competences,
			ClasseCarac caracBase, boolean montrerCompetencesApprises) {
	}

	/**
	 * Modifie l'apparence du système (fond d'affichage des boites de dialogues par exemple)
	 * @param etire Vrai = étiré, Faux = Mosaique
	 * @param premierePolice Vrai = première police, Faux = seconde police
	 * @param nomApparence Nom du fichier contenant l'apparence
	 */
	public default void Systeme_modifierApparence(boolean etire, boolean premierePolice, String nomApparence) {
	}

	/**
	 * Modifie l'apparence d'un personnage
	 * @param idHeros Numéro du personnage
	 * @param charset Charset à utiliser
	 * @param numeroCharset Numéro du personnage sur la planche
	 * @param transparent Si vrai le personnage est transparent
	 */
	public default void Systeme_modifierApparenceHeros(int idHeros, String charset, int numeroCharset,
			boolean transparent) {
	}

	/**
	 * Modifie l'apparence d'un véhicule
	 * @param vehicule Le véhicule
	 * @param charset La planche de charset
	 * @param numeroCharset Le numéro du véhicule sur la planche
	 */
	public default void Systeme_modifierApparenceVehicule(ExecEnum.Vehicule vehicule, String charset,
			int numeroCharset) {
	}

	/**
	 * Modifie les commandes auxquelles un héros peut accéder
	 * @param idHeros Le numéro du héros
	 * @param ajout Si vrai la commande est ajoutée
	 * @param numeroCommande Le numéro de la commande dans la base de données
	 */
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

