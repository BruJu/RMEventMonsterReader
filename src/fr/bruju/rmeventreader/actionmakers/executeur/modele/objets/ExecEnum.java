package fr.bruju.rmeventreader.actionmakers.executeur.modele.objets;

public class ExecEnum {
	public enum TypeEffet {
		AUCUN,
		ROTATION,
		VAGUE
	}

	public enum Meteo {
		AUCUNE,
		PLUIE,
		NEIGE,
		BROUILLARD,
		TEMPETE
	}
	
	public enum Intensite {
		FAIBLE,
		MOYENNE,
		FORTE
	}
	
	public enum CombatComportementFuite {
		IMPOSSIBLE,
		ARRET,
		BRANCHE
	}
	
	public enum Direction {
		INCHANGEE,
		HAUT,
		DROITE,
		BAS,
		GAUCHE
	}

	public enum ConditionsDeCombat {
		NORMAL,
		ENNEMISURPRIS,
		EQUIPESURPRISE,
		ENNEMIENCERCLE,
		EQUIPEENCERCLEE
	}

	public enum Transition {
		FONDU,
		BLOCS,
		BLOCSVERSLEHAUT,
		BLOCSVERSLEBAS,
		STORE,
		LIGNESVERTICALES,
		LIGNESHORIZONTALES,
		CARRERETRECISSANT,
		CARREGRANDISSANT,
		ECRANBAS,
		ECRANHAUT,
		ECRANGAUCHE,
		ECRANDROITE,
		SPLITVERTICALE,
		SPLITHORIZONTAL,
		QUADRUPLESPLIT,
		ZOOM,
		MOSAIQUES,
		ONDULATIONS,
		INSTANTANE,
		AUCUN,
		DEFAUT
	}

	public enum SujetTransition {
		TELEPORTATION,
		COMBAT,
		FINCOMBAT
	}

	public enum Musique {
		COMBAT,
		VICTOIRE,
		AUBERGE,
		RADEAU,
		BATEAU,
		VAISSEAU,
		FINDUJEU
	}
	
	public enum EffetSonore {
		CURSEUR,
		VALIDER,
		ANNULER,
		BUZZER,
		TRANSITIONCOMBAT,
		FUITE,
		ATTAQUEENNEMIE,
		ENNEMITOUCHE,
		ALLIETOUCHE,
		ESQUIVE,
		ENNEMIVAINCU,
		OBJETUTILISE
	}

	public enum Vehicule {
		RADEAU,
		BATEAU,
		VAISSEAU
	}

	private ExecEnum() {
		
	}
	
	public static enum Position {
		HAUT,
		MILIEU,
		BAS;
	}
	
	public static enum ChoixQCM {
		IGNORE,
		CHOIX1,
		CHOIX2,
		CHOIX3,
		CHOIX4,
		ANNULER
	}
	
	public static enum StatNiveau {
		Niveau,
		Experience
	}
	
	
}
