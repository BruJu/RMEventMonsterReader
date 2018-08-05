package fr.bruju.rmeventreader.implementation.recomposeur;

import fr.bruju.rmeventreader.actionmakers.actionner.AutoActionMaker;
import fr.bruju.rmeventreader.implementation.recomposeur.actionmaker.ComposeurInitial;

public class Recomposition {
	private final static String CHEMIN_PARAMETRES = "ressources\\recomposeur\\Parametres.txt";
	private final static String CHEMIN_ATTAQUES = "ressources\\recomposeur\\attaques";
	
	
	
	public static void exploiter() {
		Parametres parametres = new Parametres(CHEMIN_PARAMETRES);
		
		
		ComposeurInitial composeur = new ComposeurInitial(null);
		AutoActionMaker autoActionMaker = new AutoActionMaker(composeur, null);
		
		
		
		
	}
}
