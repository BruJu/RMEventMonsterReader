package fr.bruju.rmeventreader.actionmakers.executeur.modele.objets;

public class ExecEnum {
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
