package fr.bruju.rmeventreader.dictionnaires;

import fr.bruju.lcfreader.services.LecteurDeLCF;
import fr.bruju.rmeventreader.Parametre;

public class LecteurDeLCF$ extends LecteurDeLCF {
	// En l'état actuel, il s'agit simplement d'un point d'entrée unique vers la classe implémentant la lecture de
	// fichiers
	
	/* =========
	 * SINGLETON
	 * ========= */
	
	private static LecteurDeLCF$ instance;

	private LecteurDeLCF$() {
		super(Parametre.get("DOSSIER"));
	}

	public static LecteurDeLCF$ getInstance() {
		if (null == instance) {
			instance = new LecteurDeLCF$();
		}
		return instance;
	}

}
