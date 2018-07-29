package fr.bruju.rmeventreader.implementation.monsterlist.metier;

import fr.bruju.rmeventreader.utilitaire.Pair;

// TODO : fichier ressource

/**
 * Statistiques avec leurs positions
 * 
 * @author Bruju
 *
 */
public enum Positions {
	/** ID du monstre */
	POS_ID(549, 550, 551),
	/** Niveau du monstre */
	POS_NIV(555, 556, 557),
	/** EXP */
	POS_EXP(574, 575, 576),
	/** Capacité */
	POS_CAPA(577, 578, 579),
	/** Argent */
	POS_ARGENT(594, 595, 596),
	/** Points de vie */
	POS_HP(514, 516, 517),
	/** Force */
	POS_FORCE(533, 534, 535),
	/** Vitalité */
	POS_DEFENSE(530, 531, 532),
	/** Magie */
	POS_MAGIE(613, 614, 615),
	/** Esprit */
	POS_ESPRIT(570, 571, 572),
	/** Dextérité */
	POS_DEXTERITE(527, 528, 529),
	/** Esquive */
	POS_ESQUIVE(536, 537, 538);

	/** Nom de monstres par combats */
	public static final int NB_MONSTRES_MAX_PAR_COMBAT = 3;
	/** Nombre de statistiques */
	public static final int TAILLE = Positions.POS_ESQUIVE.ordinal() + 1;
	
	/** Liste des positions de variables*/
	public int[] ids;
	
	/**
	 * Positions des variables pour la statistique
	 * @param idMonstre1 Position pour le monstre 1
	 * @param idMonstre2 Position pour le monstre 2
	 * @param idMonstre3 Position pour le monstre 3
	 */
	Positions(int idMonstre1, int idMonstre2, int idMonstre3) {
		ids = new int[]{idMonstre1, idMonstre2, idMonstre3};
	}
	
	/**
	 * Renvoie la statistique et le numéro du monstre si la variable concerne une variable
	 */
	public static Pair<Positions, Integer> searchNumVariable(int variable) {
		for (Positions position : Positions.values()) {
			for (int i = 0 ; i != NB_MONSTRES_MAX_PAR_COMBAT ; i++) {
				if (variable == position.ids[i]) {
					return new Pair<>(position, i);
				}
			}
		}
		
		return null;
	}
	
	/**
	 * Renvoie le numéro du monstre si la variable concerne l'état fossile
	 */
	public static Integer chercherFossile(int variable) {
		int[] fossile = {537, 538, 539};
		
		for (int i = 0 ; i != NB_MONSTRES_MAX_PAR_COMBAT ; i++) {
			if (fossile[i] == variable) {
				return i;
			}
		}
		
		return null;
	}

	/**
	 * Donne le header pour les statistiques
	 */
	public static String getCSVHeader() {
		return "NIVEAU;EXP;POINTCAPA;ARGENT;HP;FORCE;DEFENSE;MAGIE;ESPRIT;DEXTERITE;ESQUIVE";
	}
}