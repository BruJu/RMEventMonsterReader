package fr.bruju.rmeventreader.actionmakers.executeur.modele.objets;

public class ExecEnum {
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
}
