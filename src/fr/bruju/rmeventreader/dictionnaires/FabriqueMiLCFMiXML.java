package fr.bruju.rmeventreader.dictionnaires;

import fr.bruju.lcfreader.services.LecteurDeLCF;

public class FabriqueMiLCFMiXML extends LecteurDeLCF {
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
