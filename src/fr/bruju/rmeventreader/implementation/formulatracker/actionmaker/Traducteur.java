package fr.bruju.rmeventreader.implementation.formulatracker.actionmaker;

public class Traducteur {

	
	
	
	
	
	
	
	// Singleton
	private static Traducteur instance;

	private Traducteur() {
	}

	public static Traducteur getInstance() {
		if (null == instance) {
			instance = new Traducteur();
		}
		return instance;
	}
}
