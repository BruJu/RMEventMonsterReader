package fr.bruju.rmeventreader.dictionnaires;

import fr.bruju.lcfreader.services.LecteurDeLCF;

public class FabriqueMiLCFMiXML extends LecteurDeLCF {
	// En l'état actuel, il s'agit simplement d'un point d'entrée unique vers la classe implémentant la lecture de
	// fichiers
	
	/* =========
	 * SINGLETON
	 * ========= */
	
	private static FabriqueMiLCFMiXML instance;

	private FabriqueMiLCFMiXML() {
		super("ressources\\FichiersBruts\\");
	}

	public static FabriqueMiLCFMiXML getInstance() {
		if (null == instance) {
			instance = new FabriqueMiLCFMiXML();
		}
		return instance;
	}

}
